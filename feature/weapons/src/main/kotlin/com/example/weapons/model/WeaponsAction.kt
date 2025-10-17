package com.example.weapons.model

import androidx.compose.runtime.Immutable

@Immutable
internal sealed interface WeaponsAction {
    data class ShowError(val message: String): WeaponsAction
    data class NavigateToWeaponDetail(val weaponsUUID: String): WeaponsAction
}