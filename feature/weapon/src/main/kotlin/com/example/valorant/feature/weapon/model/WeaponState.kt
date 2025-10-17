package com.example.valorant.feature.weapon.model

import androidx.compose.runtime.Immutable
import com.example.valorant.domain.model.weapon.detail.WeaponDetail
import com.example.valorant.domain.state.StateWrapper

@Immutable
internal data class WeaponState(
    val weapon: StateWrapper<WeaponDetail> = StateWrapper.loading()
)