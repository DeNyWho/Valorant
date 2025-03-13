package com.example.agents.model.state

import com.example.valorant.domain.model.agent.role.AgentRole

internal data class AgentsUiState(
    val selectedRole: AgentRole? = null,
)