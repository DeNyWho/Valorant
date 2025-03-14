package com.example.valorant.data.local.dao.agent

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.valorant.data.local.model.agent.AgentAbilityEntity

@Dao
interface AgentAbilityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAbilities(abilities: List<AgentAbilityEntity>)
}