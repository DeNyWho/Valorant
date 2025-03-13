package com.example.valorant.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.example.agents.navigation.AgentsRoute
import com.example.agents.navigation.agentsScreen
import com.example.maps.navigation.mapsScreen
import com.example.settings.navigation.settingsScreen
import com.example.valorant.feature.agent.navigation.agentScreen
import com.example.valorant.feature.agent.navigation.navigateToAgent
import com.example.valorant.feature.map.navigation.mapScreen
import com.example.valorant.feature.map.navigation.navigateToMap
import com.example.valorant.feature.weapon.navigation.navigateToWeapon
import com.example.valorant.feature.weapon.navigation.weaponScreen
import com.example.valorant.ui.ValorantAppState
import com.example.weapons.navigation.weaponsScreen

@Composable
fun ValorantNavHost(
    appState: ValorantAppState,
    modifier: Modifier = Modifier,
) {
    val navController = appState.navController

    NavHost(
        navController = navController,
        startDestination = AgentsRoute,
        modifier = modifier,
    ) {
        agentsScreen(
            onAgentClick = navController::navigateToAgent,
        )
        agentScreen(
            onBackClick = navController::popBackStack,
        )
        mapsScreen(
            onMapClick = navController::navigateToMap,
        )
        mapScreen(
            onBackClick = navController::popBackStack,
        )
        weaponsScreen(
            onWeaponClick = navController::navigateToWeapon,
        )
        weaponScreen(
            onBackClick = navController::popBackStack,
        )
        settingsScreen()
    }
}