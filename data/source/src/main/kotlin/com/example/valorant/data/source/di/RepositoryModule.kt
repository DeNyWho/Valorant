package com.example.valorant.data.source.di

import com.example.valorant.data.source.repository.user.settings.UserSettingsRepositoryImpl
import com.example.valorant.domain.repository.user.settings.UserSettingsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule{

    @Binds
    fun bindUserSettingsRepository(userSettingsRepositoryImpl: UserSettingsRepositoryImpl): UserSettingsRepository

}