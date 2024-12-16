package com.example.valorant.core.uikit.component.navigation

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.example.valorant.core.uikit.R
import com.example.valorant.core.uikit.component.icon.ValorantIcon
import com.example.valorant.core.uikit.theme.grey600
import com.example.valorant.core.uikit.util.DefaultPreview

@Composable
fun RowScope.ValorantNavigationBarItem(
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    alwaysShowLabel: Boolean = true,
    icon: @Composable () -> Unit,
    label: @Composable (() -> Unit)? = null,
) {
    NavigationBarItem(
        selected = selected,
        onClick = onClick,
        icon = icon,
        modifier = modifier,
        enabled = enabled,
        label = label,
        alwaysShowLabel = alwaysShowLabel,
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = MaterialTheme.colorScheme.primary,
            unselectedIconColor = grey600,
            selectedTextColor = MaterialTheme.colorScheme.primary,
            unselectedTextColor = grey600,
            indicatorColor = Color.Transparent,
        ),
    )
}

@PreviewLightDark
@Composable
private fun PreviewValorantNavigationBarItem() {
    DefaultPreview {
        ValorantNavigationBar {
            ValorantNavigationBarItem(
                icon = {
                    ValorantIcon(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(R.drawable.valorant),
                        contentDescription = null,
                    )
                },
                selected = true,
                label = {
                    Text(
                        text = "TEST",
                        style = MaterialTheme.typography.titleSmall,
                    )
                },
                onClick = { },
            )
            ValorantNavigationBarItem(
                icon = {
                    ValorantIcon(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(R.drawable.valorant),
                        contentDescription = null,
                    )
                },
                selected = false,
                label = {
                    Text(
                        text = "TEST",
                        style = MaterialTheme.typography.titleSmall,
                    )
                },
                onClick = { },
            )
        }
    }
}

@Composable
fun ValorantNavigationBar(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit,
) {
    NavigationBar(
        modifier = modifier,
        content = content,
        containerColor = MaterialTheme.colorScheme.background,
    )
}
