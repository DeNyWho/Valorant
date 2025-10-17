package com.example.valorant.data.source.repository.weapon

import com.example.valorant.data.local.dao.DataUpdateDao
import com.example.valorant.data.local.dao.weapon.WeaponDao
import com.example.valorant.data.local.dao.weapon.WeaponRangesDao
import com.example.valorant.data.local.dao.weapon.WeaponSkinDao
import com.example.valorant.data.local.mappers.weapon.toDetail
import com.example.valorant.data.local.mappers.weapon.toLight
import com.example.valorant.data.local.model.DataUpdateEntity
import com.example.valorant.data.local.model.weapon.WeaponRangesEntity
import com.example.valorant.data.local.model.weapon.WeaponSkinEntity
import com.example.valorant.data.network.model.dto.weapon.WeaponDTO
import com.example.valorant.data.network.service.weapon.WeaponService
import com.example.valorant.data.source.mapper.weapon.toEntity
import com.example.valorant.domain.model.common.request.ApiError
import com.example.valorant.domain.model.common.request.Resource
import com.example.valorant.domain.model.weapon.detail.WeaponDetail
import com.example.valorant.domain.model.weapon.light.WeaponLight
import com.example.valorant.domain.repository.weapon.WeaponRepository
import com.example.valorant.domain.state.StateListWrapper
import com.example.valorant.domain.state.StateWrapper
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.util.UUID
import javax.inject.Inject

internal class WeaponRepositoryImpl @Inject constructor(
    private val weaponService: WeaponService,
    private val weaponDao: WeaponDao,
    private val weaponSkinDao: WeaponSkinDao,
    private val weaponRangesDao: WeaponRangesDao,
    private val dataUpdateDao: DataUpdateDao,
): WeaponRepository {
    override fun getWeapons(): Flow<StateListWrapper<WeaponLight>> = flow {
        emit(StateListWrapper.loading())

        val localWeapons = weaponDao.getAllWeapons()
        val shouldFetch = dataUpdateDao.isUpdateExpired(DATA_TYPE, System.currentTimeMillis())
        val dataExists = dataUpdateDao.doesDataExist(DATA_TYPE) > 0

        if (localWeapons.isNotEmpty() && !shouldFetch) {
            emit(StateListWrapper.success(localWeapons.map { it.toLight() }.toPersistentList()))
            return@flow
        }

        val state = when (val result = weaponService.getWeapons()) {
            is Resource.Success -> {
                try {
                    saveWeaponsToDatabase(result.data.data)
                    val savedWeapons = weaponDao.getAllWeapons()

                    if (savedWeapons.isNotEmpty()) {
                        updateDataTimestamp(dataExists)

                        StateListWrapper.success(savedWeapons.map { it.toLight() }.toPersistentList())
                    } else {
                        StateListWrapper.error(
                            error = ApiError.Unknown("No weapons saved to database")
                        )
                    }
                } catch (e: Exception) {
                    StateListWrapper.error(
                        error = ApiError.Unknown("Failed to save weapons: ${e.message}", e)
                    )
                }
            }

            is Resource.Error -> {
                if (localWeapons.isNotEmpty()) {
                    StateListWrapper.success(localWeapons.map { it.toLight() }.toPersistentList())
                } else {
                    StateListWrapper.error(error = result.error)
                }
            }

            is Resource.Loading -> {
                StateListWrapper.loading()
            }
        }

        emit(state)
    }.flowOn(Dispatchers.IO)

    override fun getWeaponDetail(uuid: String): Flow<StateWrapper<WeaponDetail>> = flow {
        emit(StateWrapper.loading())

        val localWeapon = weaponDao.getWeaponWithDetails(uuid)

        if (localWeapon != null) {
            emit(StateWrapper.success(localWeapon.toDetail()))
            return@flow
        }

        val state = when (val result = weaponService.getWeaponDetail(uuid)) {
            is Resource.Success -> {
                val weaponData = result.data.data

                if (weaponData == null) {
                    StateWrapper.error(
                        error = ApiError.HttpError(
                            statusCode = 404,
                            message = "Weapon not found on server"
                        )
                    )
                } else {
                    try {
                        saveWeaponsToDatabase(listOf(weaponData))
                        val savedWeapon = weaponDao.getWeaponWithDetails(uuid)

                        if (savedWeapon != null) {
                            StateWrapper.success(savedWeapon.toDetail())
                        } else {
                            StateWrapper.error(
                                error = ApiError.Unknown("Failed to retrieve saved weapon from database")
                            )
                        }
                    } catch (e: Exception) {
                        StateWrapper.error(
                            error = ApiError.Unknown("Failed to save weapon: ${e.message}", e)
                        )
                    }
                }
            }

            is Resource.Error -> {
                StateWrapper.error(error = result.error)
            }

            is Resource.Loading -> {
                StateWrapper.loading()
            }
        }

        emit(state)
    }.flowOn(Dispatchers.IO)

    private suspend fun saveWeaponsToDatabase(weapons: List<WeaponDTO>) {
        if (weapons.isEmpty()) return

        weapons.forEach { weapon ->
            try {
                val weaponEntity = weapon.toEntity()
                weaponDao.insertWeapon(weaponEntity)

                val skins = weapon.skins.map { skin ->
                    WeaponSkinEntity(
                        uuid = skin.uuid,
                        weaponUuid = weaponEntity.uuid,
                        displayName = skin.displayName,
                        displayIcon = skin.displayIcon,
                    )
                }
                if (skins.isNotEmpty()) {
                    weaponSkinDao.insertSkins(skins)
                }

                weapon.weaponStats?.let { stats ->
                    val weaponRanges = stats.damageRanges.map { damageRange ->
                        WeaponRangesEntity(
                            uuid = UUID.randomUUID().toString(),
                            weaponUuid = weaponEntity.uuid,
                            rangeStartMeters = damageRange.rangeStartMeters,
                            rangeEndMeters = damageRange.rangeEndMeters,
                            headDamage = damageRange.headDamage,
                            bodyDamage = damageRange.bodyDamage,
                            legDamage = damageRange.legDamage,
                        )
                    }
                    if (weaponRanges.isNotEmpty()) {
                        weaponRangesDao.insertRanges(weaponRanges)
                    }
                }
            } catch (e: Exception) {
                throw RuntimeException(
                    "Failed to save weapon ${weapon.uuid} (${weapon.displayName}): ${e.message}",
                    e
                )
            }
        }
    }

    private suspend fun updateDataTimestamp(exists: Boolean) {
        val now = System.currentTimeMillis()

        if (exists) {
            dataUpdateDao.updateNextUpdateTime(DATA_TYPE, now, UPDATE_INTERVAL)
        } else {
            dataUpdateDao.insertUpdate(
                DataUpdateEntity(
                    dataType = DATA_TYPE,
                    lastUpdatedAt = now,
                    nextUpdateAt = now + UPDATE_INTERVAL,
                )
            )
        }
    }

    companion object {
        const val UPDATE_INTERVAL: Long = 30 * 60 * 1000
        const val DATA_TYPE = "weapons"
    }
}