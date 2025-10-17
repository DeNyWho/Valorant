package com.example.agents.model

import androidx.compose.runtime.Immutable
import com.example.valorant.domain.model.agent.light.AgentLight
import com.example.valorant.domain.model.agent.role.AgentRole
import com.example.valorant.domain.state.StateListWrapper

@Immutable
internal data class AgentsState(
    val agents: StateListWrapper<AgentLight> = StateListWrapper.loading(),
    val roles: StateListWrapper<AgentRole> = StateListWrapper.loading(),
    val selectedRole: AgentRole? = null,
)