package com.example.valorant.data.source.repository.user

import com.example.valorant.data.datastore.source.UserDataSource
import com.example.valorant.domain.model.common.device.FontSize
import com.example.valorant.domain.model.common.device.ThemeType
import com.example.valorant.domain.model.user.LanguageType
import com.example.valorant.domain.repository.user.settings.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.mapNotNull
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDataSource: UserDataSource,
): UserRepository {
    private val userSettings = userDataSource.userData

    override val isFirstLaunch: Flow<Boolean>
        get() = userSettings["is_first_launch"]?.mapNotNull { it as? Boolean }
            ?: flow { emit(true) }

    override val fontSize: Flow<FontSize>
        get() = userSettings["font_size"]?.mapNotNull { it as? FontSize }
            ?: flow { emit(FontSize.DEFAULT) }

    override val theme: Flow<ThemeType>
        get() = userSettings["theme"]?.mapNotNull { it as? ThemeType }
            ?: flow { emit(ThemeType.SYSTEM) }

    override val language: Flow<LanguageType>
        get() = userSettings["language"]?.mapNotNull { it as? LanguageType }
            ?: flow { emit(LanguageType.SYSTEM) }

    override suspend fun updateFirstLaunch(isFirstLaunch: Boolean) =
        userDataSource.updateFirstLaunch(isFirstLaunch)

    override suspend fun updateFontSize(fontSize: FontSize) =
        userDataSource.updateFontSize(fontSize)

    override suspend fun updateTheme(theme: ThemeType) =
        userDataSource.updateTheme(theme)

    override suspend fun updateLanguage(language: LanguageType) =
        userDataSource.updateLanguage(language)
}