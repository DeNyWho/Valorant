package com.example.valorant.domain.repository.agent

import com.example.valorant.domain.model.agent.detail.AgentDetail
import com.example.valorant.domain.model.agent.light.AgentLight
import com.example.valorant.domain.state.StateListWrapper
import com.example.valorant.domain.state.StateWrapper
import kotlinx.coroutines.flow.Flow

interface AgentRepository {
    fun getAgents(): Flow<StateListWrapper<AgentLight>>
    fun getAgentDetail(uuid: String): Flow<StateWrapper<AgentDetail>>
}