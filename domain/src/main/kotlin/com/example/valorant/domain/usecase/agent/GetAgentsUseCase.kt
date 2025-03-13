package com.example.valorant.domain.usecase.agent

import com.example.valorant.domain.model.agent.light.AgentLight
import com.example.valorant.domain.model.agent.role.AgentRole
import com.example.valorant.domain.repository.agent.AgentRepository
import com.example.valorant.domain.state.StateListWrapper
import kotlinx.coroutines.flow.Flow

class GetAgentsUseCase(private val agentRepository: AgentRepository) {
    operator fun invoke(role: AgentRole? = null): Flow<StateListWrapper<AgentLight>> {
        return agentRepository.getAgents(role)
    }
}