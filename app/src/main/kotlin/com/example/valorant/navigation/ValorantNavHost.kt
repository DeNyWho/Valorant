package com.example.valorant.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.example.valorant.feature.agent.navigation.agentScreen
import com.example.valorant.feature.agent.navigation.navigateToAgent
import com.example.valorant.feature.explore.navigation.EXPLORE_ROUTE
import com.example.valorant.feature.explore.navigation.exploreScreen
import com.example.valorant.feature.map.navigation.mapScreen
import com.example.valorant.feature.map.navigation.navigateToMap
import com.example.valorant.feature.weapon.navigation.navigateToWeapon
import com.example.valorant.feature.weapon.navigation.weaponScreen
import com.example.valorant.ui.ValorantAppState

@Composable
fun ValorantNavHost(
    appState: ValorantAppState,
    modifier: Modifier = Modifier,
    startDestination: String = EXPLORE_ROUTE,
) {
    val navController = appState.navController

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        exploreScreen(
            onAgentPressed = navController::navigateToAgent,
            onMapPressed = navController::navigateToMap,
            onWeaponPressed = navController::navigateToWeapon,
        )
        agentScreen(
            onBackPressed = navController::popBackStack,
        )
        mapScreen(
            onBackPressed = navController::popBackStack,
        )
        weaponScreen(
            onBackPressed = navController::popBackStack,
        )
    }
}