package com.example.valorant.feature.weapon.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.valorant.feature.weapon.WeaponScreen
import kotlinx.serialization.Serializable

@Serializable
data class WeaponRoute(val weaponId: String)

fun NavController.navigateToWeapon(weaponId: String, navOptions: NavOptions? = null) {
    navigate(route = WeaponRoute(weaponId), navOptions)
}

fun NavGraphBuilder.weaponScreen(
    onBackClick: () -> Boolean,
) {
    composable<WeaponRoute> { backStackEntry ->
        val weaponId = backStackEntry.arguments?.getString("weaponId") ?: throw IllegalArgumentException("WeaponScreen requires a non-null id")

        WeaponScreen(
            weaponId = weaponId,
            onBackClick = onBackClick,
        )
    }
}