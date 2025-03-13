package com.example.valorant.feature.map.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.valorant.feature.map.MapScreen
import kotlinx.serialization.Serializable

@Serializable
data class MapRoute(val mapId: String)

fun NavController.navigateToMap(mapId: String, navOptions: NavOptions? = null) {
    navigate(route = MapRoute(mapId), navOptions)
}

fun NavGraphBuilder.mapScreen(
    onBackClick: () -> Boolean,
) {
    composable<MapRoute> { backStackEntry ->
        val mapId = backStackEntry.arguments?.getString("mapId") ?: throw IllegalArgumentException("MapScreen requires a non-null id")

        MapScreen(
            mapId = mapId,
            onBackClick = onBackClick,
        )
    }
}