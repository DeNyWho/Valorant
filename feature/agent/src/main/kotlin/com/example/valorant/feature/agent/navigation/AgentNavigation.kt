package com.example.valorant.feature.agent.navigation

import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.valorant.feature.agent.AgentScreen

const val AGENT_ROUTE = "agent_route"

fun NavController.navigateToAgent(uuid: String, navOptions: NavOptions? = null) = navigate("${AGENT_ROUTE}?uuid=$uuid", navOptions)

fun NavGraphBuilder.agentScreen(
    onBackPressed: () -> Boolean,
) {
    composable(
        "$AGENT_ROUTE?uuid={uuid}",
        arguments = listOf(
            navArgument("uuid") { type = NavType.StringType },
        ),
    ) {
        val uuid = remember { it.arguments?.getString("uuid") }

        AgentScreen(
            uuid = uuid ?: throw IllegalArgumentException("AgentScreen requires a non-null uuid"),
            onBackPressed = onBackPressed,
        )
    }
}