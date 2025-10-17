package com.example.valorant.feature.agent.model

import androidx.compose.runtime.Immutable

@Immutable
internal sealed interface AgentAction {
    data class ShowError(val message: String): AgentAction
    data object NavigateUp: AgentAction
}