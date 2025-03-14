package com.example.maps.component.item

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
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
internal fun CardMapGridItemShimmer(
    modifier: Modifier = Modifier,
    shimmer: Shimmer,
) {
    Card(
        modifier = modifier
            .shimmer(shimmer)
            .clip(MaterialTheme.shapes.medium),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 2.dp,
        ),
    ) {

    }
}

fun LazyGridScope.showCardMapGridItemShimmer(
    modifier: Modifier,
    shimmer: Shimmer,
    count: Int = 6,
) {
    items(count) {
        CardMapGridItemShimmer(
            modifier = modifier,
            shimmer = shimmer,
        )
    }
}

@Preview
@Composable
private fun PreviewCardMapGridItemShimmer() {
    DefaultPreview {
        CardMapGridItemShimmer(
            modifier = Modifier.size(CardMapGridItemDefaults.Size.GridMedium),
            shimmer = rememberShimmer(ShimmerBounds.Custom),
        )
    }
}