package com.example.valorant.data.source.di

import com.example.valorant.data.datastore.source.UserDataSource
import com.example.valorant.data.local.dao.DataUpdateDao
import com.example.valorant.data.local.dao.agent.AgentAbilityDao
import com.example.valorant.data.local.dao.agent.AgentDao
import com.example.valorant.data.local.dao.agent.AgentRoleDao
import com.example.valorant.data.local.dao.map.MapCalloutDao
import com.example.valorant.data.local.dao.map.MapDao
import com.example.valorant.data.network.service.agent.AgentService
import com.example.valorant.data.network.service.map.MapService
import com.example.valorant.data.network.service.weapon.WeaponService
import com.example.valorant.data.source.repository.agent.AgentRepositoryImpl
import com.example.valorant.data.source.repository.map.MapRepositoryImpl
import com.example.valorant.data.source.repository.user.UserRepositoryImpl
import com.example.valorant.data.source.repository.weapon.WeaponRepositoryImpl
import com.example.valorant.domain.repository.agent.AgentRepository
import com.example.valorant.domain.repository.map.MapRepository
import com.example.valorant.domain.repository.user.settings.UserRepository
import com.example.valorant.domain.repository.weapon.WeaponRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object SourceModule {

    @Provides
    @Singleton
    fun provideAgentRepository(
        agentService: AgentService,
        agentDao: AgentDao,
        agentRoleDao: AgentRoleDao,
        agentAbilityDao: AgentAbilityDao,
        dataUpdateDao: DataUpdateDao,
    ): AgentRepository {
        return AgentRepositoryImpl(
            agentService = agentService,
            agentDao = agentDao,
            agentRoleDao = agentRoleDao,
            agentAbilityDao = agentAbilityDao,
            dataUpdateDao = dataUpdateDao,
        )
    }

    @Provides
    @Singleton
    fun provideWeaponRepository(
        weaponService: WeaponService,
    ): WeaponRepository {
        return WeaponRepositoryImpl(
            weaponService = weaponService,
        )
    }

    @Provides
    @Singleton
    fun provideMapRepository(
        mapService: MapService,
        mapDao: MapDao,
        mapCalloutDao: MapCalloutDao,
        dataUpdateDao: DataUpdateDao,
    ): MapRepository {
        return MapRepositoryImpl(
            mapService = mapService,
            mapDao = mapDao,
            mapCalloutDao = mapCalloutDao,
            dataUpdateDao = dataUpdateDao,
        )
    }

    @Provides
    @Singleton
    fun provideUserRepository(
        userDataSource: UserDataSource,
    ): UserRepository {
        return UserRepositoryImpl(userDataSource)
    }
}