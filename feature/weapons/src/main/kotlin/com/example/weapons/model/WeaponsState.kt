package com.example.weapons.model

import androidx.compose.runtime.Immutable
import com.example.valorant.domain.model.weapon.light.WeaponLight
import com.example.valorant.domain.state.StateListWrapper

@Immutable
internal data class WeaponsState(
    val weapons: StateListWrapper<WeaponLight> = StateListWrapper.loading(),
)