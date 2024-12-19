package com.example.valorant.domain.repository.weapon

import com.example.valorant.domain.model.weapon.detail.WeaponDetail
import com.example.valorant.domain.model.weapon.light.WeaponLight
import com.example.valorant.domain.state.StateListWrapper
import com.example.valorant.domain.state.StateWrapper
import kotlinx.coroutines.flow.Flow

interface WeaponRepository {

    fun getWeapons(): Flow<StateListWrapper<WeaponLight>>
    fun getWeaponDetail(uuid: String): Flow<StateWrapper<WeaponDetail>>
}