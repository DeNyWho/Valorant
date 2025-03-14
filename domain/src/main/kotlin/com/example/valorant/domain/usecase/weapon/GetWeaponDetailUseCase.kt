package com.example.valorant.domain.usecase.weapon

import com.example.valorant.domain.model.weapon.detail.WeaponDetail
import com.example.valorant.domain.repository.weapon.WeaponRepository
import com.example.valorant.domain.state.StateWrapper
import kotlinx.coroutines.flow.Flow

//class GetWeaponDetailUseCase(private val weaponRepository: WeaponRepository) {
//    operator fun invoke(uuid: String): Flow<StateWrapper<WeaponDetail>> {
//        return weaponRepository.getWeaponDetail(uuid)
//    }
//}