package com.example.valorant.data.source.repository.agent

import com.example.valorant.data.local.dao.DataUpdateDao
import com.example.valorant.data.local.dao.agent.AgentAbilityDao
import com.example.valorant.data.local.dao.agent.AgentDao
import com.example.valorant.data.local.dao.agent.AgentRoleDao
import com.example.valorant.data.local.mappers.agent.toDetail
import com.example.valorant.data.local.mappers.agent.toLight
import com.example.valorant.data.local.mappers.agent.toRole
import com.example.valorant.data.local.model.DataUpdateEntity
import com.example.valorant.data.local.model.agent.AgentRoleEntity
import com.example.valorant.data.network.model.dto.agent.AgentDTO
import com.example.valorant.data.network.service.agent.AgentService
import com.example.valorant.data.source.mapper.agent.toEntity
import com.example.valorant.domain.model.agent.detail.AgentDetail
import com.example.valorant.domain.model.agent.light.AgentLight
import com.example.valorant.domain.model.agent.role.AgentRole
import com.example.valorant.domain.model.common.request.ApiError.HttpError
import com.example.valorant.domain.model.common.request.ApiError.Unknown
import com.example.valorant.domain.model.common.request.Resource
import com.example.valorant.domain.repository.agent.AgentRepository
import com.example.valorant.domain.state.StateListWrapper
import com.example.valorant.domain.state.StateWrapper
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

internal class AgentRepositoryImpl @Inject constructor(
    private val agentService: AgentService,
    private val agentDao: AgentDao,
    private val agentRoleDao: AgentRoleDao,
    private val agentAbilityDao: AgentAbilityDao,
    private val dataUpdateDao: DataUpdateDao,
): AgentRepository {
    override fun getAgents(role: AgentRole?): Flow<StateListWrapper<AgentLight>> = flow {
        emit(StateListWrapper.loading())

        val localAgents = agentDao.getAllAgents(role?.uuid)
        val shouldFetch = dataUpdateDao.isUpdateExpired(DATA_TYPE, System.currentTimeMillis())
        val dataExists = dataUpdateDao.doesDataExist(DATA_TYPE) > 0

        if (localAgents.isNotEmpty() && !shouldFetch) {
            emit(StateListWrapper.success(localAgents.map { it.toLight() }.toPersistentList()))
            return@flow
        }

        val state = when (val result = agentService.getAgents()) {
            is Resource.Success -> {
                try {
                    saveAgentsToDatabase(result.data.data)
                    val savedAgents = agentDao.getAllAgents(role?.uuid)

                    if (savedAgents.isNotEmpty()) {
                        updateDataTimestamp(dataExists)
                        StateListWrapper.success(
                            savedAgents.map { it.toLight() }.toPersistentList()
                        )
                    } else {
                        StateListWrapper.error(
                            error = Unknown("No agents saved to database")
                        )
                    }
                } catch (e: Exception) {
                    StateListWrapper.error(
                        error = Unknown("Failed to save agents: ${e.message}", e)
                    )
                }
            }
            is Resource.Error -> {
                if (localAgents.isNotEmpty()) {
                    StateListWrapper.success(
                        localAgents.map { it.toLight() }.toPersistentList()
                    )
                } else {
                    StateListWrapper.error(error = result.error)
                }
            }

            Resource.Loading -> {
                StateListWrapper.loading()
            }
        }

        emit(state)
    }.flowOn(Dispatchers.IO)

    override fun getAgentDetail(uuid: String): Flow<StateWrapper<AgentDetail>> = flow {
        emit(StateWrapper.loading())

        val localAgent = agentDao.getAgentWithDetails(uuid)

        if (localAgent != null) {
            emit(StateWrapper.success(localAgent.toDetail()))
            return@flow
        }

        val state = when (val result = agentService.getAgentDetail(uuid)) {
            is Resource.Success -> {
                val agentData = result.data.data

                if (agentData == null) {
                    StateWrapper.error(
                        error = HttpError(
                            statusCode = 404,
                            message = "Agent not found on server"
                        )
                    )
                } else {
                    try {
                        saveAgentsToDatabase(listOf(agentData))
                        val savedAgent = agentDao.getAgentWithDetails(uuid)

                        if (savedAgent != null) {
                            StateWrapper.success(savedAgent.toDetail())
                        } else {
                            StateWrapper.error(
                                error = Unknown("Failed to retrieve saved agent from database")
                            )
                        }
                    } catch (e: Exception) {
                        StateWrapper.error(
                            error = Unknown("Failed to save agent: ${e.message}", e)
                        )
                    }
                }
            }
            is Resource.Error -> {
                StateWrapper.error(error = result.error)
            }

            Resource.Loading -> {
                StateWrapper.loading()
            }
        }

        emit(state)
    }.flowOn(Dispatchers.IO)

    override fun getAgentsRoles(): Flow<StateListWrapper<AgentRole>> {
        val rolesFlow = agentRoleDao.getAllRoles()
        val agentsFlow = agentDao.getAllAgentsFlow(null)

        return combine(rolesFlow, agentsFlow) { roles, agents ->
            if (agents.isEmpty()) {
                StateListWrapper.loading()
            } else {
                StateListWrapper.success(roles.map { it.toRole() }.toPersistentList())
            }
        }.flowOn(Dispatchers.IO)
    }

    private suspend fun saveAgentsToDatabase(agents: List<AgentDTO>) {
        if (agents.isEmpty()) return

        val uniqueRoles = agents
            .map { agent ->
                AgentRoleEntity(
                    uuid = agent.role.uuid,
                    displayName = agent.role.displayName,
                    displayIcon = agent.role.displayIcon,
                )
            }
            .distinctBy { it.uuid }

        uniqueRoles.forEach { agentRoleDao.insertRole(it) }

        agents.forEach { agent ->
            try {
                agentDao.insertAgent(agent.toEntity())

                val abilities = agent.abilities.map { it.toEntity(agent.uuid) }
                if (abilities.isNotEmpty()) {
                    agentAbilityDao.insertAbilities(abilities)
                }
            } catch (e: Exception) {
                throw RuntimeException(
                    "Failed to save agent ${agent.uuid} (${agent.displayName}): ${e.message}",
                    e
                )
            }
        }
    }

    private suspend fun updateDataTimestamp(exists: Boolean) {
        val now = System.currentTimeMillis()

        if (exists) {
            dataUpdateDao.updateNextUpdateTime(DATA_TYPE, now, UPDATE_INTERVAL)
        } else {
            dataUpdateDao.insertUpdate(
                DataUpdateEntity(
                    dataType = DATA_TYPE,
                    lastUpdatedAt = now,
                    nextUpdateAt = now + UPDATE_INTERVAL,
                )
            )
        }
    }

    companion object {
        const val UPDATE_INTERVAL: Long = 30 * 60 * 1000
        const val DATA_TYPE = "agents"
    }
}