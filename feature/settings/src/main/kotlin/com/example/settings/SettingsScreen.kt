package com.example.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.settings.component.theme.ThemeComponent
import com.example.valorant.core.uikit.component.topbar.SimpleTopBar
import com.example.valorant.core.uikit.util.DefaultPreview
import com.example.valorant.domain.model.common.device.ThemeType

@Composable
internal fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel(),
) {
    val selectedThemeState by viewModel.selectedTheme.collectAsState()

    SettingsUI(
        selectedTheme = selectedThemeState,
        onUpdateTheme = { theme ->
            viewModel.updateThemeSettings(theme)
        }
    )
}

@Composable
private fun SettingsUI(
    selectedTheme: ThemeType,
    onUpdateTheme: (ThemeType) -> Unit,
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            SimpleTopBar(
                onBackPressed = null,
                title = stringResource(R.string.feature_settings_top_bar_title),
                titleStyle = MaterialTheme.typography.titleLarge,
                tonalElevation = 4.dp,
                shadowElevation = 4.dp,
            )
        },
    ) { padding ->
        SettingsContentUI(
            modifier = Modifier
                .padding(padding),
            selectedTheme = selectedTheme,
            onUpdateTheme = onUpdateTheme,
        )
    }
}

@Composable
private fun SettingsContentUI(
    modifier: Modifier,
    selectedTheme: ThemeType,
    onUpdateTheme: (ThemeType) -> Unit,
) {
    val lazyColumnState = rememberLazyListState()

    val settingsItems = listOf<@Composable () -> Unit>(
        {
            ThemeComponent(
                selectedTheme = selectedTheme,
                onUpdateTheme = onUpdateTheme,
            )
        },
    )

    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(
            top = 16.dp,
        ),
        state = lazyColumnState,
    ) {
        settingsItems.forEachIndexed { index, item ->
            item {
                item()
                if (index < settingsItems.size - 1) {
                    Spacer(
                        modifier = Modifier
                            .padding(
                                vertical = 8.dp,
                            )
                            .height(1.dp)
                            .background(Color.LightGray.copy(0.1f))
                            .fillMaxWidth(),
                    )
                }
            }
        }
    }
}