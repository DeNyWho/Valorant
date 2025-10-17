package com.example.valorant.feature.map.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.valorant.feature.map.MapScreen
import kotlinx.serialization.Serializable

@Serializable
data class MapRoute(val mapUUID: String)

fun NavController.navigateToMap(mapUUID: String, navOptions: NavOptions? = null) {
    navigate(route = MapRoute(mapUUID), navOptions)
}

fun NavGraphBuilder.mapScreen(
    onBackClick: () -> Boolean,
) {
    composable<MapRoute> { backStackEntry ->
        MapScreen(
            onBackClick = onBackClick,
        )
    }
}