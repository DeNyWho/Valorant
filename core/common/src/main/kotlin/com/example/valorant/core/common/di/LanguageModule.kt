package com.example.valorant.core.common.di

import com.example.valorant.core.common.util.language.LanguageManager
import com.example.valorant.core.common.util.language.SystemLanguageManager
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class LanguageModule {

    @Binds
    internal abstract fun bindsLanguageManager(
        languageManager: SystemLanguageManager,
    ): LanguageManager
}