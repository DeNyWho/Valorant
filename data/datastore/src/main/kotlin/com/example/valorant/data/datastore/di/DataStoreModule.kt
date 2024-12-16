package com.example.valorant.data.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.example.valorant.data.datastore.serializer.UserSettingsStorageSerializer
import com.example.valorant.data.datastore.source.UserSettingsSource
import com.example.valorant.domain.model.user.settings.UserSettings
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
internal object DataStoreModule {

    @Provides
    @Singleton
    fun provideUserSettingsStorageSerializer(): UserSettingsStorageSerializer {
        return UserSettingsStorageSerializer()
    }

    @Provides
    @Singleton
    fun provideUserSettingsSource(dataStore: DataStore<UserSettings>): UserSettingsSource {
        return UserSettingsSource(dataStore)
    }

    @Provides
    @Singleton
    fun provideUserSettingsStore(
        localStorageSerializer: UserSettingsStorageSerializer,
        @ApplicationContext context: Context
    ): DataStore<UserSettings> {
        return DataStoreFactory.create(
            serializer = localStorageSerializer,
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
            migrations = listOf()
        ) {
            context.dataStoreFile("user_settings.pb")
        }
    }

}