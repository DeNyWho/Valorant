package com.example.valorant.core.uikit.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.example.valorant.core.uikit.util.LocalScreenInfo
import com.example.valorant.domain.model.common.device.ThemeType
import org.jetbrains.annotations.VisibleForTesting

/**
 * Dark default theme color scheme
 */
@VisibleForTesting
val DarkColorScheme = darkColorScheme(
    background = blue900,
    onBackground = grey50,
    surfaceVariant = grey200,
    onSurfaceVariant = grey300,
    scrim = grey900,
    onSurface = grey600,
    surface = grey800,
    primary = red400,
    onPrimary = grey50,
    surfaceContainer = blue900,
    primaryContainer = red400,
    onPrimaryContainer = grey50,
)

/**
 * Light default theme color scheme
 */
@VisibleForTesting
val LightColorScheme = lightColorScheme(
    background = grey50,
    onBackground = blue900,
    surfaceVariant = grey200,
    onSurfaceVariant = grey300,
    scrim = grey900,
    onSurface = grey600,
    surface = grey800,
    primary = red400,
    onPrimary = grey50,
    surfaceContainer = grey50,
    primaryContainer = red400,
    onPrimaryContainer = grey50,
)

/**
 * Valorant theme.
 *
 * @param darkTheme Whether the theme should use a dark color scheme (follows system by default).
 */
@Composable
fun ValorantTheme(
    themeType: ThemeType = ThemeType.SYSTEM,
    content: @Composable () -> Unit,
) {
    val colorScheme = when (themeType) {
        ThemeType.DARK -> DarkColorScheme
        ThemeType.LIGHT -> LightColorScheme
        ThemeType.SYSTEM -> {
            if (!isSystemInDarkTheme()) {
                LightColorScheme
            } else {
                DarkColorScheme
            }
        }
    }

    val darkTheme = when (themeType) {
        ThemeType.DARK -> true
        ThemeType.LIGHT -> false
        ThemeType.SYSTEM -> {
            isSystemInDarkTheme()
        }
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars =
                !darkTheme // negate darkTheme
            WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars =
                !darkTheme
        }
    }

    val screenInfo = LocalScreenInfo.current
    val fontSizePrefs = screenInfo.fontSizePrefs

    MaterialTheme(
        colorScheme = colorScheme,
        typography = valorantTypography(fontSizePrefs = fontSizePrefs),
        content = content,
    )
}