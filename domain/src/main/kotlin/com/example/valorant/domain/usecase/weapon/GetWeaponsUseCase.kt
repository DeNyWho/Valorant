package com.example.valorant.domain.usecase.weapon

import com.example.valorant.domain.model.weapon.light.WeaponLight
import com.example.valorant.domain.repository.weapon.WeaponRepository
import com.example.valorant.domain.state.StateListWrapper
import kotlinx.coroutines.flow.Flow

class GetWeaponsUseCase(private val weaponRepository: WeaponRepository) {
    operator fun invoke(): Flow<StateListWrapper<WeaponLight>> {
        return weaponRepository.getWeapons()
    }
}