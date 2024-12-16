package com.example.valorant.data.source.repository.user.settings

import com.example.valorant.data.datastore.source.UserSettingsSource
import com.example.valorant.domain.model.common.device.FontSize
import com.example.valorant.domain.repository.user.settings.UserSettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.mapNotNull
import javax.inject.Inject

class UserSettingsRepositoryImpl @Inject constructor(
    private val userDataSource: UserSettingsSource,
): UserSettingsRepository {
    private val userSettings = userDataSource.userSettings

    override val isFirstLaunch: Flow<Boolean>
        get() = userSettings["is_first_launch"]?.mapNotNull { it as? Boolean }
            ?: flow { emit(true) }

    override val fontSize: Flow<FontSize>
        get() = userSettings["font_size"]?.mapNotNull { it as? FontSize }
            ?: flow { emit(FontSize.DEFAULT) }

    override suspend fun updateFirstLaunch(isFirstLaunch: Boolean) =
        userDataSource.updateFirstLaunch(isFirstLaunch)

    override suspend fun updateFontSize(fontSize: FontSize) =
        userDataSource.updateFontSize(fontSize)
}