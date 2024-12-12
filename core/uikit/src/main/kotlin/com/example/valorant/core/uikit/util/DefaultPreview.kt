package com.example.valorant.core.uikit.util

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import com.example.valorant.core.uikit.theme.ValorantTheme
import com.example.valorant.domain.model.common.device.ScreenInfo

@Composable
fun DefaultPreview(needBackground: Boolean = false, content: @Composable (ColumnScope.() -> Unit)) {
    CompositionLocalProvider(
        LocalScreenInfo provides ScreenInfo()
    ) {
        ValorantTheme {
            Column(
                if(needBackground) Modifier.background(MaterialTheme.colorScheme.background) else Modifier,
                content = content,
            )
        }
    }
}