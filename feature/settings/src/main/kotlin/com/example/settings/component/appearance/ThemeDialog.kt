package com.example.settings.component.appearance

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.settings.R
import com.example.valorant.core.uikit.component.button.ValorantButtonSurface
import com.example.valorant.core.uikit.util.DefaultPreview
import com.example.valorant.domain.model.common.device.ThemeType

@Composable
internal fun ThemeDialog(
    setShowDialog: (Boolean) -> Unit,
    selectedTheme: ThemeType,
    onUpdateTheme: (ThemeType) -> Unit,
) {
    Dialog(
        onDismissRequest = { setShowDialog.invoke(false) },
    ) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.small,
            color = MaterialTheme.colorScheme.surfaceContainer,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Text(
                    text = stringResource(R.string.feature_settings_section_appearance_theme),
                    style = MaterialTheme.typography.titleMedium,
                )

                Column(Modifier.selectableGroup()) {
                    ThemeType.entries.forEach { theme ->
                        Row(
                            Modifier
                                .padding(top = 8.dp)
                                .fillMaxWidth()
                                .selectable(
                                    selected = selectedTheme == theme,
                                    onClick = {
                                        onUpdateTheme.invoke(theme)
                                        setShowDialog.invoke(false)
                                    },
                                    role = Role.RadioButton,
                                    indication = null,
                                    interactionSource = remember { MutableInteractionSource() },
                                )
                                .padding(horizontal = 16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = selectedTheme == theme,
                                onClick = null,
                            )
                            Text(
                                text = theme.toString(),
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(start = 16.dp),
                            )
                        }
                    }
                }

                ValorantButtonSurface(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .fillMaxWidth(),
                    paddingValues = PaddingValues(0.dp),
                    shape = MaterialTheme.shapes.small,
                    onClick = {
                        setShowDialog(false)
                    },
                ) {
                    Text(
                        text = stringResource(R.string.feature_settings_section_ui_theme_dialog_close),
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.error,
                    )
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun PreviewThemeDialog() {
    DefaultPreview {
        ThemeDialog(
            setShowDialog = { },
            selectedTheme = ThemeType.SYSTEM,
            onUpdateTheme = { },
        )
    }
}