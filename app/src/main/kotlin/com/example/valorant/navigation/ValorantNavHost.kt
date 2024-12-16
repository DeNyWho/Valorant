package com.example.valorant.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.example.valorant.feature.explore.navigation.EXPLORE_ROUTE
import com.example.valorant.feature.explore.navigation.exploreScreen
import com.example.valorant.feature.tournaments.navigation.tournamentsScreen
import com.example.valorant.ui.ValorantAppState

@Composable
fun ValorantNavHost(
    appState: ValorantAppState,
    modifier: Modifier = Modifier,
    startDestination: String = EXPLORE_ROUTE,
    isFirstLaunch: Boolean,
) {
    val navController = appState.navController

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        exploreScreen()

        tournamentsScreen()
    }
}