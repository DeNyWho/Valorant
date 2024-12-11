package com.example.valorant.data.local.di

import android.content.Context
import androidx.room.Room
import com.example.valorant.data.local.ValorantDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {
    @Provides
    @Singleton
    fun providesValorantDatabase(
        @ApplicationContext context: Context,
    ): ValorantDatabase = Room.databaseBuilder(
        context,
        ValorantDatabase::class.java,
        "valorant-database",
    )
        .fallbackToDestructiveMigration() // Use only until the application is released in the release version
        .build()
}