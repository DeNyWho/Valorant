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

@Dao
interface AgentDao {
    @Query("SELECT * FROM agents WHERE (:roleUuid IS NULL OR roleUuid = :roleUuid)")
    suspend fun getAllAgents(roleUuid: String? = null): List<AgentEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAgent(agent: AgentEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAbilities(abilities: List<AgentAbilityEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRole(role: AgentRoleEntity)

    @Transaction
    @Query("SELECT * FROM agents WHERE uuid = :uuid")
    suspend fun getAgentWithDetails(uuid: String): AgentWithDetails?
}