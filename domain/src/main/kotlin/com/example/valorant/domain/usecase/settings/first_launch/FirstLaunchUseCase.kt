package com.example.valorant.domain.usecase.settings.first_launch

import com.example.valorant.domain.repository.user.settings.UserSettingsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FirstLaunchUseCase @Inject constructor(
    private val userSettingsRepository: UserSettingsRepository,
) {
    val isFirstLaunch: Flow<Boolean> = userSettingsRepository.isFirstLaunch

    suspend fun setFirstLaunchCompleted() {
        userSettingsRepository.updateFirstLaunch(false)
    }
}
