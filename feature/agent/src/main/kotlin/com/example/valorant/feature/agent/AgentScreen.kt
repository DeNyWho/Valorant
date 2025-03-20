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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.valorant.core.uikit.component.button.ValorantButtonSurface
import com.example.valorant.core.uikit.component.icon.ValorantIconPrimary
import com.example.valorant.core.uikit.util.clickableWithoutRipple
import com.example.valorant.domain.model.agent.detail.AgentDetail
import com.example.valorant.domain.state.StateWrapper
import com.example.valorant.feature.agent.components.abilities.AbilitiesComponent
import com.example.valorant.feature.agent.components.description.DescriptionComponent
import com.example.valorant.feature.agent.components.overview.OverviewComponent

@Composable
internal fun AgentScreen(
    viewModel: AgentViewModel = hiltViewModel(),
    agentId: String,
    onBackClick: () -> Boolean,
) {
    LaunchedEffect(viewModel) {
        viewModel.getAgent(agentId)
    }

    AgentContent(
        agent = viewModel.agent.value,
        onBackPressed = onBackClick,
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
                            onBackPressed.invoke()
                        },
                        elevation = ButtonDefaults.elevatedButtonElevation(),
                    ) {
                        ValorantIconPrimary(
                            modifier = Modifier
                                .clickableWithoutRipple {
                                    onBackPressed.invoke()
                                }
                                .size(28.dp),
                            imageVector = AutoMirrored.Filled.ArrowBack,
                            contentDescription = "back",
                        )
                    }
                    Spacer(Modifier.weight(1f))
                }
                agent.data?.let {
                    AgentUI(
                        agent = it,
                    )
                }
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

