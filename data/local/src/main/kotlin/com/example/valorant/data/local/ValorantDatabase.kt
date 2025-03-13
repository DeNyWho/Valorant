package com.example.valorant.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.valorant.data.local.converters.StringListConverter
import com.example.valorant.data.local.dao.agent.AgentDao
import com.example.valorant.data.local.model.agent.AgentAbilityEntity
import com.example.valorant.data.local.model.agent.AgentEntity
import com.example.valorant.data.local.model.agent.AgentRoleEntity

@Database(
    entities = [
        AgentEntity::class,
        AgentRoleEntity::class,
        AgentAbilityEntity::class,
    ],
    version = 1,
    exportSchema = true,
)
@TypeConverters(StringListConverter::class)
internal abstract class ValorantDatabase : RoomDatabase() {
    abstract fun agentDao(): AgentDao
}