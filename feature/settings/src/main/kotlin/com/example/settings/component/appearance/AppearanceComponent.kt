package com.example.settings.component.appearance

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
import com.example.valorant.domain.model.user.LanguageType

@Composable
internal fun AppearanceComponent(
    modifier: Modifier = Modifier,
    selectedTheme: ThemeType,
    selectedLanguage: LanguageType,
    onUpdateTheme: (ThemeType) -> Unit,
    onUpdateLanguage: (LanguageType) -> Unit,
) {
    val showDialogTheme = remember { mutableStateOf(false) }
    val showDialogLanguage = remember { mutableStateOf(false) }

    Text(
        modifier = Modifier.padding(start = 16.dp),
        text = stringResource(R.string.feature_settings_section_appearance_title),
        color = MaterialTheme.colorScheme.onBackground,
        style = MaterialTheme.typography.titleLarge,
    )

    Column {
        Column(
            modifier = Modifier
                .clickable {
                    showDialogTheme.value = true
                }
                .padding(vertical = 8.dp)
                .fillMaxWidth(),
        ) {
            Text(
                modifier = Modifier.padding(start = 16.dp),
                text = stringResource(R.string.feature_settings_section_appearance_theme),
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

        Column(
            modifier = Modifier
                .clickable {
                    showDialogLanguage.value = true
                }
                .padding(vertical = 8.dp)
                .fillMaxWidth(),
        ) {
            Text(
                modifier = Modifier.padding(start = 16.dp),
                text = stringResource(R.string.feature_settings_section_appearance_language),
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.titleMedium,
            )

            Text(
                modifier = Modifier.padding(start = 16.dp),
                text = selectedLanguage.displayName,
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.bodySmall.copy(color = Color.LightGray.copy(0.1f)),
            )
        }
    }

    if (showDialogTheme.value) {
        ThemeDialog(
            setShowDialog = {
                showDialogTheme.value = it
            },
            selectedTheme = selectedTheme,
            onUpdateTheme = onUpdateTheme,
        )
    }

    if (showDialogLanguage.value) {
        LanguageDialog (
            setShowDialog = {
                showDialogLanguage.value = it
            },
            selectedLanguage = selectedLanguage,
            onUpdateLanguage = onUpdateLanguage,
        )
    }
}