package com.example.valorant.domain.repository.user.settings

import com.example.valorant.domain.model.common.device.FontSize
import kotlinx.coroutines.flow.Flow

interface UserSettingsRepository {
    val fontSize: Flow<FontSize>
    val isFirstLaunch: Flow<Boolean>
    suspend fun updateFirstLaunch(isFirstLaunch: Boolean)
    suspend fun updateFontSize(fontSize: FontSize)
}