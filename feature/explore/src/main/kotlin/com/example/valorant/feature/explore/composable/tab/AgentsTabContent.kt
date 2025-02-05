package com.example.valorant.feature.explore.composable.tab

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.valorant.core.uikit.component.chip.ValorantChipGroupPrimary
import com.example.valorant.core.uikit.util.onUpdateShimmerBounds
import com.example.valorant.domain.model.agent.light.AgentLight
import com.example.valorant.domain.model.agent.role.AgentRole
import com.example.valorant.domain.model.common.device.ScreenInfo
import com.example.valorant.domain.model.common.device.ScreenType
import com.example.valorant.domain.state.StateListWrapper
import com.example.valorant.feature.explore.R
import com.example.valorant.feature.explore.composable.grid.agent.CardAgentGridItem
import com.example.valorant.feature.explore.composable.grid.agent.CardAgentGridItemDefaults
import com.example.valorant.feature.explore.composable.grid.agent.showCardAgentGridItemShimmer
import com.valentinilk.shimmer.Shimmer
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer
import kotlinx.coroutines.launch

@Composable
internal fun AgentsTabContent(
    screenInfo: ScreenInfo,
    roles: List<AgentRole>,
    agents: StateListWrapper<AgentLight>,
    onAgentClick: (String) -> Unit,
    onRoleSelected: (AgentRole?) -> Unit,
    shimmer: Shimmer = rememberShimmer(ShimmerBounds.Custom),
    selectedRole: AgentRole?,
) {
    val lazyGridState = rememberLazyGridState()
    val scope = rememberCoroutineScope()

    val size = when (screenInfo.screenType) {
        ScreenType.SMALL -> CardAgentGridItemDefaults.Size.GridSmall
        ScreenType.MEDIUM -> CardAgentGridItemDefaults.Size.GridMedium
        else -> CardAgentGridItemDefaults.Size.GridLarge
    }

    val filteredAgents = agents.data
    val roleIcons = roles.map { it.displayIcon }
    val chipTitles = listOf(stringResource(R.string.feature_explore_tab_chip_first)) + roles.map { it.displayName }
    val selectedChipIndex = when (selectedRole) {
        null -> 0
        else -> roles.indexOf(selectedRole) + 1
    }

    Column {
        if (agents.isLoading) {

        } else if(agents.data.isNotEmpty()) {
            ValorantChipGroupPrimary(
                chipTitles = chipTitles,
                selectedChipIndex = selectedChipIndex,
                onChipSelected = { selectedIndex ->
                    if (selectedIndex == 0) {
                        onRoleSelected(null)

                        scope.launch {
                            lazyGridState.animateScrollToItem(0)
                        }
                    } else {
                        onRoleSelected(roles[selectedIndex - 1])
                    }
                },
                roleIcons = roleIcons,
            )
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
            if (agents.isLoading) {
                showCardAgentGridItemShimmer(
                    modifier = Modifier.size(size),
                    shimmerInstance = shimmer,
                )
            } else if (filteredAgents.isNotEmpty()) {
                items(
                    filteredAgents,
                    key = { it.uuid },
                ) { agent ->
                    CardAgentGridItem(
                        modifier = Modifier.size(size),
                        agent = agent,
                        onAgentClick = onAgentClick,
                    )
                }
            }
        }
    }
}