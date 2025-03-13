package com.example.valorant.core.uikit.component.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.example.valorant.core.uikit.util.DefaultPreview

@Composable
fun ValorantButtonSurface(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    enabled: Boolean = true,
    shape: Shape = ButtonDefaults.shape,
    elevation: ButtonElevation? = null,
    border: BorderStroke? = null,
    paddingValues: PaddingValues,
    content: @Composable() (RowScope.() -> Unit)
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        shape = shape,
        colors = ButtonDefaults.buttonColors(
            contentColor = MaterialTheme.colorScheme.onSurface,
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        elevation = elevation,
        content = content,
        border = border,
        contentPadding = paddingValues,
    )
}

@PreviewLightDark
@Composable
private fun PreviewValorantButtonSurface() {
    DefaultPreview {
        ValorantButtonSurface(
            modifier = Modifier.fillMaxWidth(),
            paddingValues = PaddingValues(0.dp),
            shape = MaterialTheme.shapes.small,
            onClick = {

            },
        ) {
            Text(
                text = "Hello world",
                style = MaterialTheme.typography.labelLarge,
            )
        }
    }
}