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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.agents.components.item.CardAgentGridItem
import com.example.agents.components.item.CardAgentGridItemDefaults
import com.example.agents.components.item.showCardAgentGridItemShimmer
import com.example.agents.components.top.AgentsTopBar
import com.example.agents.model.state.AgentsUiState
import com.example.valorant.core.uikit.util.LocalScreenInfo
import com.example.valorant.core.uikit.util.onUpdateShimmerBounds
import com.example.valorant.domain.model.agent.light.AgentLight
import com.example.valorant.domain.model.agent.role.AgentRole
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
    val uiState by viewModel.uiState.collectAsState()
    val rolesState by viewModel.roles.collectAsState()
    val agentsState by viewModel.agents.collectAsState()

    AgentsUI(
        uiState = uiState,
        rolesState = rolesState,
        agentsState = agentsState,
        onRoleSelected = { role ->
            viewModel.updateSelectedRole(role)
        },
        onAgentClick = onAgentClick,
    )
}

@Composable
private fun AgentsUI(
    uiState: AgentsUiState,
    rolesState: StateListWrapper<AgentRole>,
    agentsState: StateListWrapper<AgentLight>,
    onRoleSelected: (AgentRole?) -> Unit,
    onAgentClick: (String) -> Unit,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            AgentsTopBar(
                uiState = uiState,
                roles = rolesState,
                onRoleSelected = onRoleSelected,
            )
        },
    ) { padding ->
        AgentsContentUI(
            modifier = Modifier.padding(padding),
            agentsState = agentsState,
            onAgentClick = onAgentClick,
        )
    }
}

@Composable
private fun AgentsContentUI(
    modifier: Modifier,
    agentsState: StateListWrapper<AgentLight>,
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

        if (agentsState.isLoading) {
            showCardAgentGridItemShimmer(
                modifier = Modifier.size(size),
                shimmerInstance = shimmer,
            )
        } else if (agentsState.data.isNotEmpty()) {
            items(
                agentsState.data,
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