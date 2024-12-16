package com.example.valorant.feature.tournaments.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.valorant.feature.tournaments.TournamentsScreen

const val TOURNAMENTS_ROUTE = "tournaments_route"

fun NavController.navigateToTournaments(navOptions: NavOptions? = null) = navigate(TOURNAMENTS_ROUTE, navOptions)

fun NavGraphBuilder.tournamentsScreen() {
    composable(
        route = TOURNAMENTS_ROUTE
    ) {
        TournamentsScreen()
    }
}