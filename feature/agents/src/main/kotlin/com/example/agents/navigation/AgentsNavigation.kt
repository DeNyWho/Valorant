package com.example.agents.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.agents.AgentsScreen
import kotlinx.serialization.Serializable

@Serializable data object AgentsRoute

fun NavController.navigateToAgents(navOptions: NavOptions? = null) {
    navigate(route = AgentsRoute, navOptions)
}

fun NavGraphBuilder.agentsScreen(
    onAgentClick: (String) -> Unit,
) {
    composable<AgentsRoute> {
        AgentsScreen(
            onAgentClick = onAgentClick,
        )
    }
}