package com.example.valorant.data.local.di

import com.example.valorant.data.local.ValorantDatabase
import com.example.valorant.data.local.dao.DataUpdateDao
import com.example.valorant.data.local.dao.agent.AgentAbilityDao
import com.example.valorant.data.local.dao.agent.AgentDao
import com.example.valorant.data.local.dao.agent.AgentRoleDao
import com.example.valorant.data.local.dao.map.MapCalloutDao
import com.example.valorant.data.local.dao.map.MapDao
import com.example.valorant.data.local.dao.weapon.WeaponDao
import com.example.valorant.data.local.dao.weapon.WeaponShopDataDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object DaoModule {
    @Provides
    fun provideAgentDao(
        database: ValorantDatabase,
    ): AgentDao = database.agentDao()

    @Provides
    fun provideAgentAbilityDao(
        database: ValorantDatabase,
    ): AgentAbilityDao = database.agentAbilityDao()

    @Provides
    fun provideAgentRoleDao(
        database: ValorantDatabase,
    ): AgentRoleDao = database.agentRoleDao()

    @Provides
    fun provideMapDao(
        database: ValorantDatabase,
    ): MapDao = database.mapDao()

    @Provides
    fun provideCalloutDao(
        database: ValorantDatabase,
    ): MapCalloutDao = database.calloutDao()

    @Provides
    fun provideWeaponDao(
        database: ValorantDatabase,
    ): WeaponDao = database.weaponDao()

    @Provides
    fun provideWeaponShopDataDao(
        database: ValorantDatabase,
    ): WeaponShopDataDao = database.weaponShopDataDao()

    @Provides
    fun provideDataUpdateDao(
        database: ValorantDatabase,
    ): DataUpdateDao = database.dataUpdateDao()
}