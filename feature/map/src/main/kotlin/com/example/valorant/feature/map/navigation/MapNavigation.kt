package com.example.valorant.feature.map.navigation

import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.valorant.feature.map.MapScreen

const val MAP_ROUTE = "map_route"

fun NavController.navigateToMap(uuid: String, navOptions: NavOptions? = null) = navigate("${MAP_ROUTE}?uuid=$uuid", navOptions)

fun NavGraphBuilder.mapScreen(
    onBackPressed: () -> Boolean,
) {
    composable(
        "$MAP_ROUTE?uuid={uuid}",
        arguments = listOf(
            navArgument("uuid") { type = NavType.StringType },
        ),
    ) {
        val uuid = remember { it.arguments?.getString("uuid") }

        MapScreen(
            uuid = uuid ?: throw IllegalArgumentException("MapScreen requires a non-null uuid"),
            onBackPressed = onBackPressed,
        )
    }
}