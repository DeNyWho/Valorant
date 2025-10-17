package com.example.weapons.model

import androidx.compose.runtime.Immutable

@Immutable
internal sealed interface WeaponsEvent {
    data object LoadInitialData: WeaponsEvent
    data class OnWeaponCardClick(val weaponUUID: String): WeaponsEvent
}