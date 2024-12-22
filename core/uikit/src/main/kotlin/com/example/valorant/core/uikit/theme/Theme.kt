package com.example.valorant.core.uikit.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import com.example.valorant.core.uikit.util.LocalScreenInfo
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
    surfaceContainer = blue900,
//    primaryContainer = ,
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
    surfaceContainer = grey50,
//    primaryContainer = ,
    onPrimaryContainer = grey50,
)

/**
 * Valorant theme.
 *
 * @param darkTheme Whether the theme should use a dark color scheme (follows system by default).
 */
@Composable
fun ValorantTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colorScheme = if(!darkTheme) {
        LightColorScheme
    } else {
        DarkColorScheme
    }

    val screenInfo = LocalScreenInfo.current
    val fontSizePrefs = screenInfo.fontSizePrefs

    MaterialTheme(
        colorScheme = colorScheme,
        typography = valorantTypography(fontSizePrefs = fontSizePrefs),
        content = content,
    )
}