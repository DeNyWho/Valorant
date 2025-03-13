package com.example.valorant.domain.repository.user.settings

import com.example.valorant.domain.model.common.device.FontSize
import com.example.valorant.domain.model.common.device.ThemeType
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    val fontSize: Flow<FontSize>
    val isFirstLaunch: Flow<Boolean>
    suspend fun updateFirstLaunch(isFirstLaunch: Boolean)
    suspend fun updateFontSize(fontSize: FontSize)
    val theme: Flow<ThemeType>
    suspend fun updateTheme(theme: ThemeType)
}