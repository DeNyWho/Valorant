package com.example.agents.model

import androidx.compose.runtime.Immutable
import com.example.valorant.domain.model.agent.role.AgentRole

@Immutable
internal sealed interface AgentsEvent {
    data object LoadInitialData: AgentsEvent
    data class OnAgentCardClick(val agentUUID: String): AgentsEvent
    data class SelectRole(val role: AgentRole?): AgentsEvent
}