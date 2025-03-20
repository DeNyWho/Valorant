package com.example.valorant.data.source.repository.weapon

import com.example.valorant.data.local.dao.DataUpdateDao
import com.example.valorant.data.local.dao.weapon.WeaponDao
import com.example.valorant.data.local.dao.weapon.WeaponShopDataDao
import com.example.valorant.data.local.mappers.weapon.toLight
import com.example.valorant.data.local.model.DataUpdateEntity
import com.example.valorant.data.network.model.dto.weapon.WeaponDTO
import com.example.valorant.data.network.service.weapon.WeaponService
import com.example.valorant.data.source.mapper.weapon.toEntity
import com.example.valorant.data.source.mapper.weapon.toShopDataEntity
import com.example.valorant.domain.model.common.request.Resource
import com.example.valorant.domain.model.weapon.light.WeaponLight
import com.example.valorant.domain.repository.weapon.WeaponRepository
import com.example.valorant.domain.state.StateListWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

internal class WeaponRepositoryImpl @Inject constructor(
    private val weaponService: WeaponService,
    private val weaponDao: WeaponDao,
    private val weaponShopDataDao: WeaponShopDataDao,
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

    private suspend fun saveWeaponsToDatabase(weapons: List<WeaponDTO>) {
        weapons.forEach { weapon ->
            try {
                val weaponEntity = weapon.toEntity()
                weaponDao.insertWeapon(weaponEntity)

                val shopDataEntity = weapon.toShopDataEntity(weaponEntity.uuid)
                weaponShopDataDao.insertShopData(shopDataEntity)
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