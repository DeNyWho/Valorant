package com.example.valorant.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.example.valorant.ui.ValorantAppState

@Composable
fun ValorantNavHost(
    appState: ValorantAppState,
    modifier: Modifier = Modifier,
    startDestination: String = "",
    isFirstLaunch: Boolean,
) {
    val navController = appState.navController

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {

    }
}