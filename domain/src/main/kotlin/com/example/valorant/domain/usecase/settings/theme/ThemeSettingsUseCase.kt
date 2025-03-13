package com.example.valorant.domain.usecase.settings.theme

import com.example.valorant.domain.model.common.device.ThemeType
import com.example.valorant.domain.repository.user.settings.UserRepository
import kotlinx.coroutines.flow.Flow

class ThemeSettingsUseCase(private val userRepository: UserRepository) {
    val theme: Flow<ThemeType> = userRepository.theme

    suspend fun updateTheme(theme: ThemeType) {
        userRepository.updateTheme(theme)
    }
}