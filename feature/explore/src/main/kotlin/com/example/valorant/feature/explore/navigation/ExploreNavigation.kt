package com.example.valorant.feature.explore.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.valorant.feature.explore.ExploreScreen

const val EXPLORE_ROUTE = "explore_route"

fun NavController.navigateToExplore(navOptions: NavOptions? = null) = navigate(EXPLORE_ROUTE, navOptions)

fun NavGraphBuilder.exploreScreen(
    onAgentPressed: (String) -> Unit,
    onMapPressed: (String) -> Unit,
    onWeaponPressed: (String) -> Unit,
) {
    composable(
        route = EXPLORE_ROUTE
    ) {
        ExploreScreen(
            onAgentPressed = onAgentPressed,
            onMapPressed = onMapPressed,
            onWeaponPressed = onWeaponPressed,
        )
    }
}