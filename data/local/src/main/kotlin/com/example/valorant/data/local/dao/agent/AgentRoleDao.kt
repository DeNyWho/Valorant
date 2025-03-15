package com.example.valorant.data.local.dao.agent

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.valorant.data.local.model.agent.AgentRoleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AgentRoleDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertRole(role: AgentRoleEntity)

    @Query("SELECT * FROM agent_roles")
    fun getAllRoles(): Flow<List<AgentRoleEntity>>
}