package com.example.valorant.data.source.di

import com.example.valorant.data.datastore.source.UserSettingsSource
import com.example.valorant.data.local.dao.agent.AgentDao
import com.example.valorant.data.network.service.agent.AgentService
import com.example.valorant.data.network.service.map.MapService
import com.example.valorant.data.network.service.weapon.WeaponService
import com.example.valorant.data.source.repository.agent.AgentRepositoryImpl
import com.example.valorant.data.source.repository.map.MapRepositoryImpl
import com.example.valorant.data.source.repository.user.settings.UserSettingsRepositoryImpl
import com.example.valorant.data.source.repository.weapon.WeaponRepositoryImpl
import com.example.valorant.domain.repository.agent.AgentRepository
import com.example.valorant.domain.repository.map.MapRepository
import com.example.valorant.domain.repository.user.settings.UserSettingsRepository
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
    ): AgentRepository {
        return AgentRepositoryImpl(
            agentService = agentService,
            agentDao = agentDao,
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
    ): MapRepository {
        return MapRepositoryImpl(
            mapService = mapService,
        )
    }

    @Provides
    @Singleton
    fun provideUserSettingsRepository(
        userDataSource: UserSettingsSource,
    ): UserSettingsRepository {
        return UserSettingsRepositoryImpl(userDataSource)
    }
}