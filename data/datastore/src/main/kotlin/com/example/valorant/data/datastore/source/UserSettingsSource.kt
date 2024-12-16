package com.example.valorant.data.datastore.source

import androidx.datastore.core.DataStore
import com.example.valorant.domain.model.common.device.FontSize
import com.example.valorant.domain.model.user.settings.UserSettings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserSettingsSource @Inject constructor(
    private val dataStore: DataStore<UserSettings>,
) {
    val userSettings = buildMap<String, Flow<*>> {
        put("is_first_launch", dataStore.data.map { it.isFirstLaunch })
        put("font_size", dataStore.data.map { it.fontSize })
    }

    suspend fun updateFirstLaunch(isFirstLaunch: Boolean) {
        dataStore.updateData { userSettings ->
            userSettings.copy(
                isFirstLaunch = isFirstLaunch,
            )
        }
    }

    suspend fun updateFontSize(fontSize: FontSize) {
        dataStore.updateData { userSettings ->
            userSettings.copy(
                fontSize = fontSize
            )
        }
    }
}