package com.example.settings.model

import androidx.compose.runtime.Immutable
import com.example.valorant.domain.model.common.device.ThemeType

@Immutable
internal data class SettingsState(
    val selectedTheme: ThemeType = ThemeType.SYSTEM,
)