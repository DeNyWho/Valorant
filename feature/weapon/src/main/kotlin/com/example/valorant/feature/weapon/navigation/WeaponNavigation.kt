package com.example.valorant.feature.weapon.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.valorant.feature.weapon.WeaponScreen
import kotlinx.serialization.Serializable

@Serializable
data class WeaponRoute(val weaponUUID: String)

fun NavController.navigateToWeapon(weaponUUID: String, navOptions: NavOptions? = null) {
    navigate(route = WeaponRoute(weaponUUID), navOptions)
}

fun NavGraphBuilder.weaponScreen(
    onBackClick: () -> Boolean,
) {
    composable<WeaponRoute> { backStackEntry ->
        WeaponScreen(
            onBackClick = onBackClick,
        )
    }
}