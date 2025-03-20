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
import com.example.valorant.domain.model.common.request.Resource
import com.example.valorant.domain.model.user.LanguageType
import com.example.valorant.domain.repository.agent.AgentRepository
import com.example.valorant.domain.repository.user.settings.UserRepository
import com.example.valorant.domain.state.StateListWrapper
import com.example.valorant.domain.state.StateWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

internal class AgentRepositoryImpl @Inject constructor(
    private val agentService: AgentService,
    private val agentDao: AgentDao,
    private val agentRoleDao: AgentRoleDao,
    private val agentAbilityDao: AgentAbilityDao,
    private val dataUpdateDao: DataUpdateDao,
    private val userRepository: UserRepository,
): AgentRepository {

    override fun getAgents(role: AgentRole?): Flow<StateListWrapper<AgentLight>> {
        return userRepository.language.flatMapLatest { language ->
            getAgentsForLanguage(role, language)
        }.flowOn(Dispatchers.IO)
    }

    private fun getAgentsForLanguage(role: AgentRole?, language: LanguageType): Flow<StateListWrapper<AgentLight>> {
        return flow {
            val dataTypeWithLang = "$DATA_TYPE-${language.code}"
            val localAgents = agentDao.getAllAgents(role?.uuid, language.code)
            val shouldFetch = dataUpdateDao.isUpdateExpired(dataTypeWithLang, System.currentTimeMillis())
            val dataExists = dataUpdateDao.doesDataExist(dataTypeWithLang) > 0

            if (localAgents.isNotEmpty() && !shouldFetch) {
                emit(StateListWrapper(localAgents.map { it.toLight() }))
            } else {
                emit(StateListWrapper.loading())

                when(val agentsResult = agentService.getAgents(language)) {
                    is Resource.Success -> {
                        val agents = agentsResult.data.data
                        saveAgentsToDatabase(agents, language)

                        val savedAgents = agentDao.getAllAgents(role?.uuid, language.code)

                        if(savedAgents.isNotEmpty()) {
                            if (dataExists) {
                                dataUpdateDao.updateNextUpdateTime(dataTypeWithLang, System.currentTimeMillis(), UPDATE_INTERVAL)
                            } else {
                                val nextUpdateAt = System.currentTimeMillis() + UPDATE_INTERVAL
                                val dataUpdate = DataUpdateEntity(
                                    dataType = dataTypeWithLang,
                                    lastUpdatedAt = System.currentTimeMillis(),
                                    nextUpdateAt = nextUpdateAt,
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
        }
    }

    override fun getAgentDetail(uuid: String): Flow<StateWrapper<AgentDetail>> {
        return userRepository.language.flatMapLatest { language ->
            getAgentDetailForLanguage(uuid, language)
        }.flowOn(Dispatchers.IO)
    }

    private fun getAgentDetailForLanguage(uuid: String, language: LanguageType): Flow<StateWrapper<AgentDetail>> {
        return flow {
            emit(StateWrapper.loading())

            val agentWithDetails = agentDao.getAgentWithDetails(uuid, language.code)
            if (agentWithDetails != null) {
                emit(StateWrapper(agentWithDetails.toDetail()))
            } else {
                when(val agentResult = agentService.getAgentDetail(uuid, language)) {
                    is Resource.Success -> {
                        val agentData = agentResult.data.data
                        if (agentData != null) {
                            saveAgentsToDatabase(listOf(agentData), language)

                            val savedAgent = agentDao.getAgentWithDetails(uuid, language.code)
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
        }
    }

    override suspend fun getAgentsRoles(): Flow<StateListWrapper<AgentRole>> {
        return userRepository.language.flatMapLatest { language ->
            getAgentsRolesForLanguage(language)
        }.flowOn(Dispatchers.IO)
    }

    private fun getAgentsRolesForLanguage(language: LanguageType): Flow<StateListWrapper<AgentRole>> {
        val rolesFlow = agentRoleDao.getAllRoles(language.code)
        val agentsFlow = agentDao.getAllAgentsFlow(null, language.code)

        return combine(rolesFlow, agentsFlow) { roles, agents ->
            StateListWrapper.loading<AgentRole>()
            if (agents.isEmpty()) {
                StateListWrapper.loading()
            } else {
                StateListWrapper(data = roles.map { it.toRole() })
            }
        }
    }

    private suspend fun saveAgentsToDatabase(agents: List<AgentDTO>, language: LanguageType) {
        val uniqueRoles = agents.map { agent ->
            AgentRoleEntity(
                uuid = agent.role.uuid,
                displayName = agent.role.displayName,
                displayIcon = agent.role.displayIcon,
                languageCode = language.code,
            )
        }.distinctBy { "${it.uuid}-${it.languageCode}" }

        uniqueRoles.forEach { role ->
            agentRoleDao.insertRole(role)
        }

        agents.forEach { agent ->
            try {
                val agentEntity = agent.toEntity(language.code)
                agentDao.insertAgent(agentEntity)

                val abilities = agent.abilities.map { ability ->
                    ability.toEntity(agent.uuid, language.code)
                }
                agentAbilityDao.insertAbilities(abilities)
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