package com.example.valorant.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.valorant.data.local.converters.StringListConverter
import com.example.valorant.data.local.dao.DataUpdateDao
import com.example.valorant.data.local.dao.agent.AgentAbilityDao
import com.example.valorant.data.local.dao.agent.AgentDao
import com.example.valorant.data.local.dao.agent.AgentRoleDao
import com.example.valorant.data.local.dao.map.CalloutDao
import com.example.valorant.data.local.dao.map.MapDao
import com.example.valorant.data.local.model.DataUpdateEntity
import com.example.valorant.data.local.model.agent.AgentAbilityEntity
import com.example.valorant.data.local.model.agent.AgentEntity
import com.example.valorant.data.local.model.agent.AgentRoleEntity
import com.example.valorant.data.local.model.map.MapCalloutEntity
import com.example.valorant.data.local.model.map.MapEntity

@Database(
    entities = [
        AgentEntity::class,
        AgentRoleEntity::class,
        AgentAbilityEntity::class,
        MapEntity::class,
        MapCalloutEntity::class,
        DataUpdateEntity::class,
    ],
    version = 3,
    exportSchema = true,
)
@TypeConverters(StringListConverter::class)
internal abstract class ValorantDatabase : RoomDatabase() {
    abstract fun agentDao(): AgentDao
    abstract fun agentAbilityDao(): AgentAbilityDao
    abstract fun agentRoleDao(): AgentRoleDao
    abstract fun mapDao(): MapDao
    abstract fun calloutDao(): CalloutDao
    abstract fun dataUpdateDao(): DataUpdateDao
}