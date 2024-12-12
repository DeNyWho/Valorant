package com.example.valorant.core.uikit.util

import androidx.compose.runtime.staticCompositionLocalOf
import com.example.valorant.domain.model.common.device.ScreenInfo

val LocalScreenInfo = staticCompositionLocalOf<ScreenInfo> {
    error("LocalScreenInfo not provided")
}