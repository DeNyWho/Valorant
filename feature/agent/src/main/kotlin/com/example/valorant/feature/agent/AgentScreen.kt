package com.example.valorant.feature.agent

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons.AutoMirrored
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.valorant.core.uikit.component.button.ValorantButtonSurface
import com.example.valorant.core.uikit.component.icon.ValorantIconPrimary
import com.example.valorant.core.uikit.util.clickableWithoutRipple
import com.example.valorant.domain.model.agent.detail.AgentDetail
import com.example.valorant.domain.state.StateWrapper
import com.example.valorant.feature.agent.components.abilities.AbilitiesComponent
import com.example.valorant.feature.agent.components.description.DescriptionComponent
import com.example.valorant.feature.agent.components.overview.OverviewComponent
import com.example.valorant.feature.agent.model.AgentAction
import com.example.valorant.feature.agent.model.AgentEvent
import com.example.valorant.feature.agent.model.AgentState

@Composable
internal fun AgentScreen(
    viewModel: AgentViewModel = hiltViewModel(),
    onBackClick: () -> Boolean,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val action by viewModel.action.collectAsStateWithLifecycle(initialValue = null)

    AgentContent(
        state = state,
        eventHandler = viewModel::handleEvent,
    )

    AgentActions(
        action = action,
        onBackClick = onBackClick,
    )
}

@Composable
private fun AgentContent(
    state: AgentState,
    eventHandler: (AgentEvent) -> Unit,
) {
    when(state.agent) {
        is StateWrapper.Loading -> CircularProgressIndicator()
        is StateWrapper.Success -> {
            Box {
                Row(
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                        .align(Alignment.TopCenter)
                        .zIndex(1f)
                        .fillMaxWidth(),
                ) {
                    ValorantButtonSurface(
                        modifier = Modifier.size(32.dp),
                        paddingValues = PaddingValues(4.dp),
                        shape = MaterialTheme.shapes.small,
                        onClick = {
                            eventHandler.invoke(AgentEvent.OnBack)
                        },
                        elevation = ButtonDefaults.elevatedButtonElevation(),
                    ) {
                        ValorantIconPrimary(
                            modifier = Modifier
                                .clickableWithoutRipple {
                                    eventHandler.invoke(AgentEvent.OnBack)
                                }
                                .size(28.dp),
                            imageVector = AutoMirrored.Filled.ArrowBack,
                            contentDescription = "back",
                        )
                    }
                    Spacer(Modifier.weight(1f))
                }

                AgentUI(
                    agent = state.agent.data,
                )
            }
        }
        is StateWrapper.Error -> {

        }
    }
}

@Composable
private fun AgentUI(
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


@Composable
private fun AgentActions(
    action: AgentAction?,
    onBackClick: () -> Boolean
) {
    LaunchedEffect(action) {
        when(action) {
            null -> Unit
            AgentAction.NavigateUp -> onBackClick.invoke()
            is AgentAction.ShowError -> {

            }
        }
    }
}