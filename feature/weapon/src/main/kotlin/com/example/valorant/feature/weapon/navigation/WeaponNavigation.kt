package com.example.valorant.feature.weapon.navigation

import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.valorant.feature.weapon.WeaponScreen

const val WEAPON_ROUTE = "weapon_route"

fun NavController.navigateToWeapon(uuid: String, navOptions: NavOptions? = null) = navigate("${WEAPON_ROUTE}?uuid=$uuid", navOptions)

fun NavGraphBuilder.weaponScreen(
    onBackPressed: () -> Boolean,
) {
    composable(
        "$WEAPON_ROUTE?uuid={uuid}",
        arguments = listOf(
            navArgument("uuid") { type = NavType.StringType },
        ),
    ) {
        val uuid = remember { it.arguments?.getString("uuid") }

        WeaponScreen(
            uuid = uuid ?: throw IllegalArgumentException("WeaponScreen requires a non-null uuid"),
            onBackPressed = onBackPressed,
        )
    }
}