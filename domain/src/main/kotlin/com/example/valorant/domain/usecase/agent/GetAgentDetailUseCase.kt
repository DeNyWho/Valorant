package com.example.valorant.domain.usecase.agent

import com.example.valorant.domain.model.agent.detail.AgentDetail
import com.example.valorant.domain.repository.agent.AgentRepository
import com.example.valorant.domain.state.StateWrapper
import kotlinx.coroutines.flow.Flow

class GetAgentDetailUseCase(private val agentRepository: AgentRepository) {
    operator fun invoke(uuid: String): Flow<StateWrapper<AgentDetail>> {
        return agentRepository.getAgentDetail(uuid)
    }
}