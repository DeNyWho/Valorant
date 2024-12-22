package com.example.valorant.core.uikit.component.tab

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.TabRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import com.example.valorant.core.uikit.util.LocalScreenInfo

@Composable
fun <T> ValorantScrollableTabRow(
    modifier: Modifier = Modifier,
    itemModifier: Modifier = Modifier,
    items: List<T>,
    selectedIndex: Int,
    onTabSelected: (Int) -> Unit,
    itemToText: @Composable (T) -> String,
    edgePadding: Int = 16,
    selectedColor: Color = MaterialTheme.colorScheme.primary,
    unSelectedColor: Color = Color.Gray,
) {
    val screenWidth = LocalScreenInfo.current.portraitWidthDp
    val tabWidth = 100.dp
    val contentFits = items.size * tabWidth <= screenWidth.dp

    if (contentFits) {
        TabRow(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = edgePadding.dp),
            containerColor = MaterialTheme.colorScheme.background,
            selectedTabIndex = selectedIndex,
        ) {
            items.forEachIndexed { index, item ->
                ValorantTab(
                    modifier = itemModifier,
                    text = itemToText(item),
                    isSelected = index == selectedIndex,
                    onClick = { onTabSelected(index) },
                    selectedColor = selectedColor,
                    unSelectedColor = unSelectedColor,
                )
            }
        }
    } else {
        ScrollableTabRow(
            selectedTabIndex = selectedIndex,
            containerColor = MaterialTheme.colorScheme.background,
            modifier = modifier,
            edgePadding = edgePadding.dp
        ) {
            items.forEachIndexed { index, item ->
                ValorantTab(
                    modifier = itemModifier,
                    text = itemToText(item),
                    isSelected = index == selectedIndex,
                    onClick = { onTabSelected(index) },
                    selectedColor = selectedColor,
                    unSelectedColor = unSelectedColor,
                )
            }
        }
    }
}