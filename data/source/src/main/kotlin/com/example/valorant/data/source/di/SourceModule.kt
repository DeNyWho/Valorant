package com.example.valorant.data.source.di

import com.example.valorant.data.datastore.source.UserSettingsSource
import com.example.valorant.data.network.service.agent.AgentService
import com.example.valorant.data.source.repository.agent.AgentRepositoryImpl
import com.example.valorant.data.source.repository.user.settings.UserSettingsRepositoryImpl
import com.example.valorant.domain.repository.agent.AgentRepository
import com.example.valorant.domain.repository.user.settings.UserSettingsRepository
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
        agentService: AgentService
    ): AgentRepository {
        return AgentRepositoryImpl(
            agentService = agentService,
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