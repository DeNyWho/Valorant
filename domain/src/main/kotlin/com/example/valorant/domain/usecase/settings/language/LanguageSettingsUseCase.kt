package com.example.valorant.domain.usecase.settings.language

import com.example.valorant.domain.model.user.LanguageType
import com.example.valorant.domain.repository.user.settings.UserRepository
import kotlinx.coroutines.flow.Flow

class LanguageSettingsUseCase(private val userRepository: UserRepository) {
    val language: Flow<LanguageType> = userRepository.language

    suspend fun updateLanguage(language: LanguageType) {
        userRepository.updateLanguage(language)
    }
}