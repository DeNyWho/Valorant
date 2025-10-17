package com.example.settings.model

import androidx.compose.runtime.Immutable
import com.example.valorant.domain.model.common.device.ThemeType

@Immutable
internal sealed interface SettingsEvent {
    data object LoadInitialData: SettingsEvent
    data class SelectTheme(val theme: ThemeType): SettingsEvent
}