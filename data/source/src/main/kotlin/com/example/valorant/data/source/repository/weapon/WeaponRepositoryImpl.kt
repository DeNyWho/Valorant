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
import com.example.valorant.domain.model.common.request.Resource
import com.example.valorant.domain.model.weapon.detail.WeaponDetail
import com.example.valorant.domain.model.weapon.light.WeaponLight
import com.example.valorant.domain.repository.weapon.WeaponRepository
import com.example.valorant.domain.state.StateListWrapper
import com.example.valorant.domain.state.StateWrapper
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
    override fun getWeapons(): Flow<StateListWrapper<WeaponLight>> {
        return flow {
            val localWeapons = weaponDao.getAllWeapons()
            val shouldFetch = dataUpdateDao.isUpdateExpired(DATA_TYPE,  System.currentTimeMillis())
            val dataExists = dataUpdateDao.doesDataExist(DATA_TYPE) > 0

            if (localWeapons.isNotEmpty() && !shouldFetch) {
                emit(StateListWrapper(localWeapons.map { it.toLight() }))
            } else {
                emit(StateListWrapper.loading())

                when(val weaponsResult = weaponService.getWeapons()) {
                    is Resource.Success -> {
                        val weapons = weaponsResult.data.data
                        saveWeaponsToDatabase(weapons)

                        val savedWeapons = weaponDao.getAllWeapons()

                        if(savedWeapons.isNotEmpty()) {
                            if (dataExists) {
                                dataUpdateDao.updateNextUpdateTime(DATA_TYPE, System.currentTimeMillis(), UPDATE_INTERVAL)
                            } else {
                                val nextUpdateAt = System.currentTimeMillis() + UPDATE_INTERVAL
                                val dataUpdate = DataUpdateEntity(
                                    dataType = DATA_TYPE,
                                    lastUpdatedAt = System.currentTimeMillis(),
                                    nextUpdateAt = nextUpdateAt,
                                )
                                dataUpdateDao.insertUpdate(dataUpdate)
                            }
                        }

                        val data = savedWeapons.map { it.toLight() }
                        emit(StateListWrapper(data))
                    }
                    is Resource.Error -> {
                        if (localWeapons.isEmpty()) {
                            emit(StateListWrapper(error = weaponsResult.error))
                        }
                    }
                    is Resource.Loading -> {
                        emit(StateListWrapper.loading())
                    }
                }
            }
        }.flowOn(Dispatchers.IO)
    }

    override fun getWeaponDetail(uuid: String): Flow<StateWrapper<WeaponDetail>> {
        return flow {
            emit(StateWrapper.loading())

            val weaponWithDetails = weaponDao.getWeaponWithDetails(uuid)
            if (weaponWithDetails != null) {
                emit(StateWrapper(weaponWithDetails.toDetail()))
            } else {
                when(val weaponResult = weaponService.getWeaponDetail(uuid)) {
                    is Resource.Success -> {
                        val weaponData = weaponResult.data.data
                        if (weaponData != null) {
                            saveWeaponsToDatabase(listOf(weaponData))

                            val savedWeapon = weaponDao.getWeaponWithDetails(uuid)
                            if (savedWeapon != null) {
                                emit(StateWrapper(savedWeapon.toDetail()))
                            }
                        }
                    }
                    is Resource.Error -> {
                        emit(StateWrapper(error = weaponResult.error))
                    }
                    is Resource.Loading -> {
                        emit(StateWrapper.loading())
                    }
                }
            }
        }.flowOn(Dispatchers.IO)
    }

    private suspend fun saveWeaponsToDatabase(weapons: List<WeaponDTO>) {
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
                weaponSkinDao.insertSkins(skins)

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
                    weaponRangesDao.insertRanges(weaponRanges)
                }
            } catch (e: Exception) {
                throw RuntimeException("Failed to save weapon ${weapon.uuid} (${weapon.displayName}): ${e.message}")
            }
        }
    }

    companion object {
        const val UPDATE_INTERVAL: Long = 30 * 60 * 1000
        const val DATA_TYPE = "weapons"
    }
}