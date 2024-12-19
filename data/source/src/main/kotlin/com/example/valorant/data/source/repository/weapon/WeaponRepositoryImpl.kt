package com.example.valorant.data.source.repository.weapon

import com.example.valorant.data.network.service.weapon.WeaponService
import com.example.valorant.data.source.mapper.weapon.toDetail
import com.example.valorant.data.source.mapper.weapon.toLight
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
import javax.inject.Inject

internal class WeaponRepositoryImpl @Inject constructor(
    private val weaponService: WeaponService,
): WeaponRepository {

    override fun getWeapons(): Flow<StateListWrapper<WeaponLight>> {
        return flow {
            emit(StateListWrapper.loading())

            val state = when(val weaponsResult = weaponService.getWeapons()) {
                is Resource.Success -> {
                    val data = weaponsResult.data.data.map { it.toLight() }
                    StateListWrapper(data)
                }
                is Resource.Error -> {
                    StateListWrapper(error = weaponsResult.error)
                }
                is Resource.Loading -> {
                    StateListWrapper.loading()
                }
            }

            emit(state)
        }.flowOn(Dispatchers.IO)
    }

    override fun getWeaponDetail(uuid: String): Flow<StateWrapper<WeaponDetail>> {
        return flow {
            emit(StateWrapper.loading())

            val state = when(val weaponsResult = weaponService.getWeaponDetail(uuid)) {
                is Resource.Success -> {
                    val data = weaponsResult.data.data?.toDetail()
                    StateWrapper(data)
                }
                is Resource.Error -> {
                    StateWrapper(error = weaponsResult.error)
                }
                is Resource.Loading -> {
                    StateWrapper.loading()
                }
            }

            emit(state)
        }.flowOn(Dispatchers.IO)
    }
}
