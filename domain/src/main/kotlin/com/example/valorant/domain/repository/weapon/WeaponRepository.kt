package com.example.valorant.domain.repository.weapon

import com.example.valorant.domain.model.weapon.light.WeaponLight
import com.example.valorant.domain.state.StateListWrapper
import kotlinx.coroutines.flow.Flow

interface WeaponRepository {
    fun getWeapons(): Flow<StateListWrapper<WeaponLight>>
}