package com.example.valorant.data.datastore.source

import androidx.datastore.core.DataStore
import com.example.valorant.domain.model.common.device.FontSize
import com.example.valorant.domain.model.common.device.ThemeType
import com.example.valorant.domain.model.user.LanguageType
import com.example.valorant.domain.model.user.UserData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserDataSource @Inject constructor(
    private val dataStore: DataStore<UserData>,
) {
    val userData = buildMap<String, Flow<*>> {
        put("is_first_launch", dataStore.data.map { it.isFirstLaunch })
        put("font_size", dataStore.data.map { it.fontSize })
        put("theme", dataStore.data.map { it.theme })
        put("language", dataStore.data.map { it.language })
    }

    suspend fun updateFirstLaunch(isFirstLaunch: Boolean) {
        dataStore.updateData { userData ->
            userData.copy(
                isFirstLaunch = isFirstLaunch,
            )
        }
    }

    suspend fun updateFontSize(fontSize: FontSize) {
        dataStore.updateData { userData ->
            userData.copy(
                fontSize = fontSize,
            )
        }
    }

    suspend fun updateTheme(theme: ThemeType) {
        dataStore.updateData { userData ->
            userData.copy(
                theme = theme,
            )
        }
    }

    suspend fun updateLanguage(language: LanguageType) {
        dataStore.updateData { userData ->
            userData.copy(
                language = language,
            )
        }
    }
}