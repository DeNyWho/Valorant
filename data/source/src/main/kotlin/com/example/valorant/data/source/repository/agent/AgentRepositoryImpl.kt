package com.example.valorant.data.source.repository.agent

import com.example.valorant.data.network.service.agent.AgentService
import com.example.valorant.data.source.mapper.agent.toDetail
import com.example.valorant.data.source.mapper.agent.toLight
import com.example.valorant.domain.model.agent.detail.AgentDetail
import com.example.valorant.domain.model.agent.light.AgentLight
import com.example.valorant.domain.model.common.request.Resource
import com.example.valorant.domain.repository.agent.AgentRepository
import com.example.valorant.domain.state.StateListWrapper
import com.example.valorant.domain.state.StateWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

internal class AgentRepositoryImpl @Inject constructor(
    private val agentService: AgentService,
): AgentRepository {

    override fun getAgents(): Flow<StateListWrapper<AgentLight>> {
        return flow {
            emit(StateListWrapper.loading())

            val state = when(val agentsResult = agentService.getAgents()) {
                is Resource.Success -> {
                    val data = agentsResult.data.data.map { it.toLight() }
                    StateListWrapper(data)
                }
                is Resource.Error -> {
                    StateListWrapper(error = agentsResult.error)
                }
                is Resource.Loading -> {
                    StateListWrapper.loading()
                }
            }

            emit(state)
        }.flowOn(Dispatchers.IO)
    }

    override fun getAgentDetail(uuid: String): Flow<StateWrapper<AgentDetail>> {
        return flow {
            emit(StateWrapper.loading())

            val state = when(val agentsResult = agentService.getAgentDetail(uuid)) {
                is Resource.Success -> {
                    val data = agentsResult.data.data?.toDetail()
                    StateWrapper(data)
                }
                is Resource.Error -> {
                    StateWrapper(error = agentsResult.error)
                }
                is Resource.Loading -> {
                    StateWrapper.loading()
                }
            }

            emit(state)
        }.flowOn(Dispatchers.IO)
    }
}