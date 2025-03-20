package com.example.valorant.data.local.dao.agent

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.valorant.data.local.model.agent.AgentRoleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AgentRoleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRole(role: AgentRoleEntity)

    @Query("SELECT * FROM agent_roles WHERE language_code = :languageCode")
    fun getAllRoles(languageCode: String): Flow<List<AgentRoleEntity>>
}