package com.example.valorant.domain.usecase.agent

import com.example.valorant.domain.model.agent.role.AgentRole
import com.example.valorant.domain.repository.agent.AgentRepository
import com.example.valorant.domain.state.StateListWrapper
import kotlinx.coroutines.flow.Flow

class GetAgentsRolesUseCase(private val agentRepository: AgentRepository) {
    suspend operator fun invoke(): Flow<StateListWrapper<AgentRole>> {
        return agentRepository.getAgentsRoles()
    }
}