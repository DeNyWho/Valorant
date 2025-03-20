package com.example.valorant.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.valorant.data.local.converters.StringListConverter
import com.example.valorant.data.local.dao.DataUpdateDao
import com.example.valorant.data.local.dao.agent.AgentAbilityDao
import com.example.valorant.data.local.dao.agent.AgentDao
import com.example.valorant.data.local.dao.agent.AgentRoleDao
import com.example.valorant.data.local.dao.map.MapCalloutDao
import com.example.valorant.data.local.dao.map.MapDao
import com.example.valorant.data.local.dao.weapon.WeaponDao
import com.example.valorant.data.local.dao.weapon.WeaponShopDataDao
import com.example.valorant.data.local.model.DataUpdateEntity
import com.example.valorant.data.local.model.agent.AgentAbilityEntity
import com.example.valorant.data.local.model.agent.AgentEntity
import com.example.valorant.data.local.model.agent.AgentRoleEntity
import com.example.valorant.data.local.model.map.MapCalloutEntity
import com.example.valorant.data.local.model.map.MapEntity
import com.example.valorant.data.local.model.weapon.WeaponEntity
import com.example.valorant.data.local.model.weapon.WeaponShopDataEntity

@Database(
    entities = [
        AgentEntity::class,
        AgentRoleEntity::class,
        AgentAbilityEntity::class,
        MapEntity::class,
        MapCalloutEntity::class,
        DataUpdateEntity::class,
        WeaponEntity::class,
        WeaponShopDataEntity::class,
    ],
    version = 5,
    exportSchema = true,
)
@TypeConverters(StringListConverter::class)
internal abstract class ValorantDatabase : RoomDatabase() {
    abstract fun agentDao(): AgentDao
    abstract fun agentAbilityDao(): AgentAbilityDao
    abstract fun agentRoleDao(): AgentRoleDao
    abstract fun mapDao(): MapDao
    abstract fun calloutDao(): MapCalloutDao
    abstract fun weaponDao(): WeaponDao
    abstract fun weaponShopDataDao(): WeaponShopDataDao
    abstract fun dataUpdateDao(): DataUpdateDao
}