package com.example.agents

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.agents.components.item.CardAgentGridItem
import com.example.agents.components.item.CardAgentGridItemDefaults
import com.example.agents.components.item.showCardAgentGridItemShimmer
import com.example.agents.components.top.AgentsTopBar
import com.example.agents.model.AgentsAction
import com.example.agents.model.AgentsEvent
import com.example.agents.model.AgentsState
import com.example.valorant.core.uikit.util.LocalScreenInfo
import com.example.valorant.core.uikit.util.onUpdateShimmerBounds
import com.example.valorant.domain.model.common.device.ScreenType
import com.example.valorant.domain.state.StateListWrapper
import com.valentinilk.shimmer.Shimmer
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer

@Composable
internal fun AgentsScreen(
    viewModel: AgentsViewModel = hiltViewModel(),
    onAgentClick: (String) -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val action by viewModel.action.collectAsStateWithLifecycle(initialValue = null)

    AgentsUI(
        state = state,
        eventHandler = viewModel::handleEvent,
    )

    AgentActions(
        action = action,
        onAgentClick = onAgentClick,
    )
}

@Composable
private fun AgentsUI(
    state: AgentsState,
    eventHandler: (AgentsEvent) -> Unit,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            AgentsTopBar(
                state = state,
                onRoleSelected = { role ->
                    eventHandler.invoke(AgentsEvent.SelectRole(role))
                },
            )
        },
    ) { padding ->
        AgentsContentUI(
            modifier = Modifier.padding(padding),
            state = state,
            onAgentClick = { agentUUID ->
                eventHandler.invoke(AgentsEvent.OnAgentCardClick(agentUUID))
            },
        )
    }
}

@Composable
private fun AgentsContentUI(
    modifier: Modifier,
    state: AgentsState,
    onAgentClick: (String) -> Unit,
    shimmer: Shimmer = rememberShimmer(ShimmerBounds.View),
) {
    val lazyGridState = rememberLazyGridState()
    val screenInfo = LocalScreenInfo.current
    val size = when (screenInfo.screenType) {
        ScreenType.SMALL -> CardAgentGridItemDefaults.Size.GridSmall
        ScreenType.MEDIUM -> CardAgentGridItemDefaults.Size.GridMedium
        else -> CardAgentGridItemDefaults.Size.GridLarge
    }

    LazyVerticalGrid(
        modifier = modifier
            .fillMaxSize()
            .onUpdateShimmerBounds(shimmer)
            .padding(start = 16.dp, end = 16.dp),
        columns = GridCells.Adaptive(minSize = size),
        state = lazyGridState,
        horizontalArrangement = CardAgentGridItemDefaults.HorizontalArrangement.Grid,
        verticalArrangement = CardAgentGridItemDefaults.VerticalArrangement.Grid,
    ) {
        item(span = { GridItemSpan(maxLineSpan) }) {
            Spacer(modifier = Modifier)
        }

        when(state.agents) {
            is StateListWrapper.Success -> {
                items(
                    state.agents.data,
                    key = { it.uuid },
                ) { agent ->
                    CardAgentGridItem(
                        modifier = Modifier.size(size),
                        agent = agent,
                        onAgentClick = onAgentClick,
                    )
                }
            }

            is StateListWrapper.Loading -> {
                showCardAgentGridItemShimmer(
                    modifier = Modifier.size(size),
                    shimmerInstance = shimmer,
                )
            }

            is StateListWrapper.Error -> {

            }
        }
    }
}

@Composable
private fun AgentActions(
    action: AgentsAction?,
    onAgentClick: (String) -> Unit,
) {
    LaunchedEffect(action) {
        when(action) {
            null -> Unit
            is AgentsAction.NavigateToAgentDetail -> onAgentClick.invoke(action.agentUUID)
            is AgentsAction.ShowError -> {

            }
        }
    }
}