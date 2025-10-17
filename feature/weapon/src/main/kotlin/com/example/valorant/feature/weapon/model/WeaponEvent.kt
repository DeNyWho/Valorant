package com.example.valorant.feature.weapon.model

import androidx.compose.runtime.Immutable

@Immutable
internal sealed interface WeaponEvent {
    data object LoadInitialData: WeaponEvent
    data object OnBack: WeaponEvent
}