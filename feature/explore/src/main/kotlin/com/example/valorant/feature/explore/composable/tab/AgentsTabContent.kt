package com.example.valorant.feature.explore.composable.tab

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.valorant.core.uikit.util.onUpdateShimmerBounds
import com.example.valorant.domain.model.agent.light.AgentLight
import com.example.valorant.domain.model.common.device.ScreenInfo
import com.example.valorant.domain.model.common.device.ScreenType
import com.example.valorant.domain.state.StateListWrapper
import com.example.valorant.feature.explore.composable.grid.agent.CardAgentGridItem
import com.example.valorant.feature.explore.composable.grid.agent.CardAgentGridItemDefaults
import com.example.valorant.feature.explore.composable.grid.agent.showCardAgentGridItemShimmer
import com.valentinilk.shimmer.Shimmer
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer

@Composable
internal fun AgentsTabContent(
    agents: StateListWrapper<AgentLight>,
    onAgentClick: (String) -> Unit,
    screenInfo: ScreenInfo,
    shimmer: Shimmer = rememberShimmer(ShimmerBounds.Custom),
) {
    val lazyGridState = rememberLazyGridState()

    val size = when (screenInfo.screenType) {
        ScreenType.SMALL -> CardAgentGridItemDefaults.Size.GridSmall
        ScreenType.DEFAULT -> CardAgentGridItemDefaults.Size.GridMedium
        else -> CardAgentGridItemDefaults.Size.GridLarge
    }

    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxSize()
            .onUpdateShimmerBounds(shimmer)
            .padding(start = 16.dp, end = 16.dp),
        columns = GridCells.Adaptive(minSize = size),
        state = lazyGridState,
        horizontalArrangement = CardAgentGridItemDefaults.HorizontalArrangement.Grid,
        verticalArrangement = CardAgentGridItemDefaults.VerticalArrangement.Grid,
    ) {
        if(agents.isLoading) {
            showCardAgentGridItemShimmer(
                modifier = Modifier.size(size),
                shimmerInstance = shimmer,
            )
        } else if(agents.data.isNotEmpty()) {
            items(
                agents.data,
                key = { it.uuid },
            ) { agent ->
                CardAgentGridItem (
                    modifier = Modifier.size(size),
                    agent = agent,
                    onAgentClick = onAgentClick,
                )
            }
        }
    }
}