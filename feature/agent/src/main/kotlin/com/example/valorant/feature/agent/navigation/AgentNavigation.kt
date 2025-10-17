package com.example.valorant.feature.agent.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.valorant.feature.agent.AgentScreen
import kotlinx.serialization.Serializable

@Serializable
data class AgentRoute(val agentId: String)

fun NavController.navigateToAgent(agentId: String, navOptions: NavOptions? = null) {
    navigate(route = AgentRoute(agentId), navOptions)
}

fun NavGraphBuilder.agentScreen(
    onBackClick: () -> Boolean,
) {
    composable<AgentRoute> { backStackEntry ->

        AgentScreen(onBackClick = onBackClick)
    }
}