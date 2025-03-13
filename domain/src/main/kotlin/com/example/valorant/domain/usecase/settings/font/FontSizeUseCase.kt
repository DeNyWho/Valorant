package com.example.valorant.domain.usecase.settings.font

import com.example.valorant.domain.model.common.device.FontSize
import com.example.valorant.domain.repository.user.settings.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FontSizeUseCase @Inject constructor(
    private val userSettingsRepository: UserRepository
) {
    val fontSize: Flow<FontSize> = userSettingsRepository.fontSize

    suspend fun updateFontSize(fontSize: FontSize) {
        userSettingsRepository.updateFontSize(fontSize)
    }
}