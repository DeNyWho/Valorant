package com.example.agents.model

import androidx.compose.runtime.Immutable

@Immutable
internal sealed interface AgentsAction {
    data class ShowError(val message: String): AgentsAction
    data class NavigateToAgentDetail(val agentUUID: String): AgentsAction
}