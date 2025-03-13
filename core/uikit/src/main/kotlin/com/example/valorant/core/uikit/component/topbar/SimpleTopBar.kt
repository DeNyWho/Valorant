package com.example.valorant.core.uikit.component.topbar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons.AutoMirrored
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.valorant.core.uikit.component.icon.ValorantIconPrimary
import com.example.valorant.core.uikit.util.clickableWithoutRipple

@Composable
fun SimpleTopBar(
    onBackPressed: (() -> Unit)?,
    title: String,
    titleStyle: TextStyle = MaterialTheme.typography.titleMedium,
    tonalElevation: Dp = 0.dp,
    shadowElevation: Dp = 0.dp,
    surfaceColor: Color = MaterialTheme.colorScheme.background,
    endIcons: List<@Composable () -> Unit> = emptyList(),
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        tonalElevation = tonalElevation,
        shadowElevation = shadowElevation,
        color = surfaceColor,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp, horizontal = 16.dp)
                .statusBarsPadding(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            if(onBackPressed != null) {
                ValorantIconPrimary(
                    modifier = Modifier
                        .clickableWithoutRipple {
                            onBackPressed.invoke()
                        }
                        .size(24.dp),
                    imageVector = AutoMirrored.Filled.ArrowBack,
                    contentDescription = "back",
                )
            } else {
                Spacer(Modifier)
            }

            Text(
                text = title,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                style = titleStyle,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .weight(1f),
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                endIcons.forEach { icon ->
                    icon()
                }
            }
        }
    }
}
