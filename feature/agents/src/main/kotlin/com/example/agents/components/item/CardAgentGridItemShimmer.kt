package com.example.agents.components.item

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.valentinilk.shimmer.Shimmer
import com.valentinilk.shimmer.shimmer

@Composable
internal fun CardAgentGridItemShimmer(
    modifier: Modifier = Modifier,
    shimmerInstance: Shimmer,
) {
    Card(
        modifier = modifier
            .shimmer(shimmerInstance)
            .clip(MaterialTheme.shapes.medium),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 2.dp,
        ),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.onSurfaceVariant),
        )
    }
}

fun LazyGridScope.showCardAgentGridItemShimmer(
    modifier: Modifier,
    shimmerInstance: Shimmer,
    count: Int = 20,
) {
    items(count) {
        CardAgentGridItemShimmer(
            modifier = modifier,
            shimmerInstance = shimmerInstance,
        )
    }
}