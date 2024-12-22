package com.example.valorant.core.uikit.component.tab

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun ValorantTab(
    modifier: Modifier = Modifier,
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    selectedColor: Color = MaterialTheme.colorScheme.primary,
    unSelectedColor: Color = Color.Gray,
) {
    Box(
        modifier = modifier
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            )
            .padding(8.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text,
            color = if (isSelected) selectedColor else unSelectedColor,
            textAlign = TextAlign.Center
        )
    }
}