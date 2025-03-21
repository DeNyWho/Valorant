package com.example.settings.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.settings.SettingsScreen
import kotlinx.serialization.Serializable

@Serializable data object SettingsRoute

fun NavController.navigateToSettings(navOptions: NavOptions? = null) {
    navigate(route = SettingsRoute, navOptions)
}

fun NavGraphBuilder.settingsScreen() {
    composable<SettingsRoute> {
        SettingsScreen()
    }
}