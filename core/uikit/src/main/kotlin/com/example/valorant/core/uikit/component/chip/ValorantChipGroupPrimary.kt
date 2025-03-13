package com.example.valorant.core.uikit.component.chip

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.valorant.core.uikit.util.DefaultPreview
import com.valentinilk.shimmer.Shimmer
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer
import com.valentinilk.shimmer.shimmer

@Composable
fun ValorantChipGroupPrimary(
    modifier: Modifier = Modifier,
    chipTitles: List<String>,
    selectedChipIndex: Int?,
    onChipSelected: (Int) -> Unit,
    roleIcons: List<String>? = null,
) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(
            start = 8.dp,
            end = 8.dp,
        ),
    ) {
        itemsIndexed(chipTitles) { index, title ->
            val iconUrl = if (index == 0) null else roleIcons?.getOrElse(index - 1) { null }

            ValorantChipPrimary(
                title = title,
                isSelected = selectedChipIndex == index,
                onSelect = { onChipSelected(index) },
                iconUrl = iconUrl,
                iconSize = 12.dp,
                isLarge = true,
            )
        }
    }
}

@Preview
@Composable
private fun PreviewValorantChipGroupPrimary() {
    DefaultPreview {
        ValorantChipGroupPrimary(
            chipTitles = listOf("All", "Support"),
            selectedChipIndex = 0,
            onChipSelected = {},
        )
    }
}

@Composable
fun ValorantChipGroupShimmer(
    modifier: Modifier = Modifier,
    shimmer: Shimmer = rememberShimmer(ShimmerBounds.Custom)
) {
    LazyRow(
        modifier = modifier
            .shimmer(shimmer),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(
            start = 8.dp,
            end = 8.dp,
        ),
    ) {
        items(5) {
            Surface(
                modifier = Modifier
                    .height(28.dp)
                    .width(60.dp),
                shape = MaterialTheme.shapes.large,
                color = MaterialTheme.colorScheme.onSurface,
                contentColor = MaterialTheme.colorScheme.onSurface,
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = MaterialTheme.colorScheme.onSurfaceVariant),
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewValorantChipGroupShimmer() {
    DefaultPreview {
        ValorantChipGroupShimmer()
    }
}