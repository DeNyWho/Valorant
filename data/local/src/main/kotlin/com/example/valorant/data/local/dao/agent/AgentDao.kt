package com.example.valorant.data.local.dao.agent

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.valorant.data.local.model.agent.AgentAbilityEntity
import com.example.valorant.data.local.model.agent.AgentEntity
import com.example.valorant.data.local.model.agent.AgentRoleEntity
import com.example.valorant.data.local.model.agent.AgentWithDetails
import com.example.valorant.domain.model.agent.role.AgentRole
import kotlinx.coroutines.flow.Flow

@Dao
interface AgentDao {
    @Query("SELECT * FROM agents WHERE (:roleUuid IS NULL OR roleUuid = :roleUuid)")
    suspend fun getAllAgents(roleUuid: String? = null): List<AgentEntity>

    @Query("SELECT * FROM agents WHERE (:roleUuid IS NULL OR roleUuid = :roleUuid)")
    fun getAllAgentsFlow(roleUuid: String? = null): Flow<List<AgentEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAgent(agent: AgentEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAbilities(abilities: List<AgentAbilityEntity>)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertRole(role: AgentRoleEntity)

    @Transaction
    @Query("SELECT * FROM agents WHERE uuid = :uuid")
    suspend fun getAgentWithDetails(uuid: String): AgentWithDetails?

    @Query("SELECT * FROM agent_roles")
    fun getAllRoles(): Flow<List<AgentRoleEntity>>
}