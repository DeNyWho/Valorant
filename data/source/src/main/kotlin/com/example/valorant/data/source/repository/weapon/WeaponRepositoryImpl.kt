package com.example.valorant.data.source.repository.weapon

import com.example.valorant.data.network.service.weapon.WeaponService
import com.example.valorant.domain.repository.weapon.WeaponRepository
import javax.inject.Inject

internal class WeaponRepositoryImpl @Inject constructor(
    private val weaponService: WeaponService,
): WeaponRepository {

}
