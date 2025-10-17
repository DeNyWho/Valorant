package com.example.valorant.feature.weapon.model

import androidx.compose.runtime.Immutable

@Immutable
internal sealed interface WeaponAction {
    data class ShowError(val message: String): WeaponAction
    data object NavigateUp: WeaponAction
}