package com.example.valorant.data.source.repository.agent

import com.example.valorant.data.local.dao.DataUpdateDao
import com.example.valorant.data.local.dao.agent.AgentDao
import com.example.valorant.data.local.mappers.agent.toDetail
import com.example.valorant.data.local.mappers.agent.toLight
import com.example.valorant.data.local.mappers.agent.toRole
import com.example.valorant.data.local.model.DataUpdateEntity
import com.example.valorant.data.local.model.agent.AgentAbilityEntity
import com.example.valorant.data.local.model.agent.AgentEntity
import com.example.valorant.data.local.model.agent.AgentRoleEntity
import com.example.valorant.data.network.model.dto.agent.AgentDTO
import com.example.valorant.data.network.service.agent.AgentService
import com.example.valorant.domain.model.agent.detail.AgentDetail
import com.example.valorant.domain.model.agent.light.AgentLight
import com.example.valorant.domain.model.agent.role.AgentRole
import com.example.valorant.domain.model.common.request.Resource
import com.example.valorant.domain.repository.agent.AgentRepository
import com.example.valorant.domain.state.StateListWrapper
import com.example.valorant.domain.state.StateWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

internal class AgentRepositoryImpl @Inject constructor(
    private val agentService: AgentService,
    private val agentDao: AgentDao,
    private val dataUpdateDao: DataUpdateDao,
): AgentRepository {
    override fun getAgents(role: AgentRole?): Flow<StateListWrapper<AgentLight>> {
        return flow {
            val localAgents = agentDao.getAllAgents(role?.uuid)
            val shouldFetch = dataUpdateDao.isUpdateExpired(DATA_TYPE,  System.currentTimeMillis())
            val dataExists = dataUpdateDao.doesDataExist(DATA_TYPE) > 0

            if (localAgents.isNotEmpty() && !shouldFetch) {
                emit(StateListWrapper(localAgents.map { it.toLight() }))
            } else {
                emit(StateListWrapper.loading())

                when(val agentsResult = agentService.getAgents()) {
                    is Resource.Success -> {
                        val agents = agentsResult.data.data
                        saveAgentsToDatabase(agents)

                        val savedAgents = agentDao.getAllAgents(role?.uuid)

                        if(savedAgents.isNotEmpty()) {
                            if (dataExists) {
                                dataUpdateDao.updateNextUpdateTime(DATA_TYPE, System.currentTimeMillis(), UPDATE_INTERVAL)
                            } else {
                                val nextUpdateAt = System.currentTimeMillis() + UPDATE_INTERVAL
                                val dataUpdate = DataUpdateEntity(
                                    dataType = DATA_TYPE,
                                    lastUpdatedAt = System.currentTimeMillis(),
                                    nextUpdateAt = nextUpdateAt
                                )
                                dataUpdateDao.insertUpdate(dataUpdate)
                            }
                        }

                        val data = savedAgents.map { it.toLight() }
                        emit(StateListWrapper(data))
                    }
                    is Resource.Error -> {
                        if (localAgents.isEmpty()) {
                            emit(StateListWrapper(error = agentsResult.error))
                        }
                    }
                    is Resource.Loading -> {
                        emit(StateListWrapper.loading())
                    }
                }
            }
        }.flowOn(Dispatchers.IO)
    }

    override fun getAgentDetail(uuid: String): Flow<StateWrapper<AgentDetail>> {
        return flow {
            emit(StateWrapper.loading())

            val agentWithDetails = agentDao.getAgentWithDetails(uuid)
            if (agentWithDetails != null) {
                emit(StateWrapper(agentWithDetails.toDetail()))
            } else {
                when(val agentResult = agentService.getAgentDetail(uuid)) {
                    is Resource.Success -> {
                        val agentData = agentResult.data.data
                        if (agentData != null) {
                            saveAgentsToDatabase(listOf(agentData))

                            val savedAgent = agentDao.getAgentWithDetails(uuid)
                            if (savedAgent != null) {
                                emit(StateWrapper(savedAgent.toDetail()))
                            }
                        }
                    }
                    is Resource.Error -> {
                        emit(StateWrapper(error = agentResult.error))
                    }
                    is Resource.Loading -> {
                        emit(StateWrapper.loading())
                    }
                }
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getAgentsRoles(): Flow<StateListWrapper<AgentRole>> {
        val rolesFlow = agentDao.getAllRoles()
        val agentsFlow = agentDao.getAllAgentsFlow(null)

        return combine(rolesFlow, agentsFlow) { roles, agents ->
            if (agents.isEmpty()) {
                StateListWrapper.loading()
            } else {
                StateListWrapper(data = roles.map { it.toRole() })
            }
        }.flowOn(Dispatchers.IO)
    }

    private suspend fun saveAgentsToDatabase(agents: List<AgentDTO>) {
        val uniqueRoles = agents.map { agent ->
            AgentRoleEntity(
                uuid = agent.role.uuid,
                displayName = agent.role.displayName,
                displayIcon = agent.role.displayIcon
            )
        }.distinctBy { it.uuid }

        uniqueRoles.forEach { role ->
            agentDao.insertRole(role)
        }

        agents.forEach { agent ->
            try {
                val agentEntity = AgentEntity(
                    uuid = agent.uuid,
                    displayName = agent.displayName,
                    displayIcon = agent.displayIcon,
                    description = agent.description,
                    fullPortrait = agent.fullPortrait,
                    fullPortraitV2 = agent.fullPortraitV2,
                    background = agent.background,
                    roleUuid = agent.role.uuid,
                    characterTags = agent.characterTags
                )
                agentDao.insertAgent(agentEntity)

                val abilities = agent.abilities.map { ability ->
                    AgentAbilityEntity(
                        agentUuid = agent.uuid,
                        slot = ability.slot.toString(),
                        displayName = ability.displayName,
                        description = ability.description,
                        displayIcon = ability.displayIcon
                    )
                }
                agentDao.insertAbilities(abilities)
            } catch (e: Exception) {
                throw RuntimeException("Failed to save agent ${agent.uuid} (${agent.displayName}): ${e.message}")
            }
        }
    }

    companion object {
        const val UPDATE_INTERVAL: Long = 30 * 60 * 1000
        const val DATA_TYPE = "agents"
    }
}