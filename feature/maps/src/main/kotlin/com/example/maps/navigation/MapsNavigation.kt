package com.example.maps.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.maps.MapsScreen
import kotlinx.serialization.Serializable

@Serializable data object MapsRoute

fun NavController.navigateToMaps(navOptions: NavOptions? = null) {
    navigate(route = MapsRoute, navOptions)
}

fun NavGraphBuilder.mapsScreen(
    onMapClick: (String) -> Unit,
) {
    composable<MapsRoute> {
        MapsScreen(
            onMapClick = onMapClick,
        )
    }
}
