package com.example.agents.components.top

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.agents.model.state.AgentsUiState
import com.example.valorant.core.uikit.component.chip.ValorantChipGroupPrimary
import com.example.valorant.core.uikit.component.chip.ValorantChipGroupShimmer
import com.example.valorant.domain.model.agent.role.AgentRole
import com.example.valorant.domain.state.StateListWrapper
import com.valentinilk.shimmer.Shimmer
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer

@Composable
internal fun AgentsTopBar(
    uiState: AgentsUiState,
    modifier: Modifier = Modifier,
    tonalElevation: Dp = 4.dp,
    shadowElevation: Dp = 4.dp,
    roles: StateListWrapper<AgentRole>,
    onRoleSelected: (AgentRole?) -> Unit,
    shimmer: Shimmer = rememberShimmer(ShimmerBounds.View),
) {
    val chipTitles = roles.data.map { it.displayName }
    val roleIcons = roles.data.map { it.displayIcon }

    Surface(
        modifier = modifier.fillMaxWidth(),
        tonalElevation = tonalElevation,
        shadowElevation = shadowElevation,
        color = MaterialTheme.colorScheme.background,
    ) {
        if(roles.isLoading) {
            ValorantChipGroupShimmer(
                modifier = Modifier.padding(vertical = 4.dp),
                shimmer = shimmer,
            )
        } else {
            ValorantChipGroupPrimary(
                modifier = Modifier.padding(vertical = 4.dp),
                chipTitles = listOf("All") + chipTitles,
                selectedChipIndex = roles.data.indexOf(uiState.selectedRole).let { if (it == -1) 0 else it + 1 },
                onChipSelected = { selectedIndex ->
                    val selectedRole = if (selectedIndex == 0) {
                        null
                    } else {
                        roles.data.getOrNull(selectedIndex - 1)
                    }
                    onRoleSelected(selectedRole)
                },
                roleIcons = roleIcons,
            )
        }
    }
}