package com.example.valorant.feature.agent

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.valorant.domain.model.agent.detail.AgentDetail
import com.example.valorant.domain.state.StateWrapper
import com.example.valorant.feature.agent.components.abilities.AbilitiesComponent
import com.example.valorant.feature.agent.components.description.DescriptionComponent
import com.example.valorant.feature.agent.components.overview.OverviewComponent

@Composable
internal fun AgentScreen(
    viewModel: AgentViewModel = hiltViewModel(),
    uuid: String,
    onBackPressed: () -> Boolean,
) {
    LaunchedEffect(viewModel) {
        viewModel.getAgent(uuid)
    }

    AgentContent(
        agent = viewModel.agent.value,
        onBackPressed = onBackPressed,
    )

}

@Composable
private fun AgentContent(
    onBackPressed: () -> Boolean,
    agent: StateWrapper<AgentDetail>,
) {
    when {
        agent.isLoading -> CircularProgressIndicator()
        agent.error.message.isNotEmpty() -> { }
        else -> {
            agent.data?.let {
                AgentUI(
                    agent = it,
                )
            }
        }
    }
}

@Composable
fun AgentUI(
    agent: AgentDetail,
) {
    val lazyColumnState = rememberLazyListState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        state = lazyColumnState,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        item {
            OverviewComponent(
                agent = agent,
            )
        }
        item {
            DescriptionComponent(
                modifier = Modifier
                    .padding(horizontal = 16.dp),
                agent = agent,
            )
        }
        item {
            AbilitiesComponent(
                modifier = Modifier
                    .padding(horizontal = 16.dp),
                abilities = agent.abilities,
            )
        }
    }
}

