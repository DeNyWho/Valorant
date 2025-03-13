package com.example.weapons.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.weapons.WeaponsScreen
import kotlinx.serialization.Serializable

@Serializable data object WeaponsRoute

fun NavController.navigateToWeapons(navOptions: NavOptions? = null) {
    navigate(route = WeaponsRoute, navOptions)
}

fun NavGraphBuilder.weaponsScreen(
    onWeaponClick: (String) -> Unit
) {
    composable<WeaponsRoute> {
        WeaponsScreen(onWeaponClick = onWeaponClick)
    }
}