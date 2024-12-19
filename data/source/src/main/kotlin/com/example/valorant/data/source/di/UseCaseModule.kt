package com.example.valorant.data.source.di

import com.example.valorant.domain.repository.agent.AgentRepository
import com.example.valorant.domain.usecase.agent.GetAgentDetailUseCase
import com.example.valorant.domain.usecase.agent.GetAgentsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object UseCaseModule {

    @Provides
    @Singleton
    fun provideGetAgentsUseCase(agentRepository: AgentRepository): GetAgentsUseCase {
        return GetAgentsUseCase(agentRepository)
    }

    @Provides
    @Singleton
    fun provideGetAgentDetailUseCase(agentRepository: AgentRepository): GetAgentDetailUseCase {
        return GetAgentDetailUseCase(agentRepository)
    }
}