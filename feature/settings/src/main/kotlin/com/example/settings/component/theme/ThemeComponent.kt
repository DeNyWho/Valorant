package com.example.settings.component.theme

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.settings.R
import com.example.valorant.domain.model.common.device.ThemeType

@Composable
internal fun ThemeComponent(
    modifier: Modifier = Modifier,
    selectedTheme: ThemeType,
    onUpdateTheme: (ThemeType) -> Unit,
) {
    val showDialog = remember { mutableStateOf(false) }

    Text(
        modifier = Modifier.padding(start = 16.dp),
        text = stringResource(R.string.feature_settings_section_ui_title),
        color = MaterialTheme.colorScheme.onBackground,
        style = MaterialTheme.typography.titleLarge,
    )

    Column(
        modifier = modifier
            .clickable {
                showDialog.value = true
            },
    ) {
        Column(
            modifier = Modifier
                .padding(vertical = 16.dp)
                .fillMaxWidth(),
        ) {
            Text(
                modifier = Modifier.padding(start = 16.dp),
                text = stringResource(R.string.feature_settings_section_ui_theme),
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.titleMedium,
            )

            Text(
                modifier = Modifier.padding(start = 16.dp),
                text = stringResource(selectedTheme.stringResId),
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.bodySmall.copy(color = Color.LightGray.copy(0.1f)),
            )
        }
    }

    if (showDialog.value) {
        ThemeDialog(
            setShowDialog = {
                showDialog.value = it
            },
            selectedTheme = selectedTheme,
            onUpdateTheme = onUpdateTheme,
        )
    }
}