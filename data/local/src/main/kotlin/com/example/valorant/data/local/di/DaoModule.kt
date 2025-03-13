package com.example.valorant.data.local.di

import com.example.valorant.data.local.ValorantDatabase
import com.example.valorant.data.local.dao.DataUpdateDao
import com.example.valorant.data.local.dao.agent.AgentDao
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
    fun provideDataUpdateDao(
        database: ValorantDatabase,
    ): DataUpdateDao = database.dataUpdateDao()
}