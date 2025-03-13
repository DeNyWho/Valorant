package com.example.valorant.data.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.example.valorant.data.datastore.serializer.UserDataStorageSerializer
import com.example.valorant.data.datastore.source.UserDataSource
import com.example.valorant.domain.model.user.UserData
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
    fun provideUserDataStorageSerializer(): UserDataStorageSerializer {
        return UserDataStorageSerializer()
    }

    @Provides
    @Singleton
    fun provideUserDataSource(dataStore: DataStore<UserData>): UserDataSource {
        return UserDataSource(dataStore)
    }

    @Provides
    @Singleton
    fun provideUserDataStore(
        localStorageSerializer: UserDataStorageSerializer,
        @ApplicationContext context: Context
    ): DataStore<UserData> {
        return DataStoreFactory.create(
            serializer = localStorageSerializer,
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
            migrations = listOf()
        ) {
            context.dataStoreFile("user_data.pb")
        }
    }

}