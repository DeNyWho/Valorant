package com.example.agents.components.top

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.agents.model.AgentsState
import com.example.valorant.core.uikit.component.chip.ValorantChipGroupPrimary
import com.example.valorant.core.uikit.component.chip.ValorantChipGroupShimmer
import com.example.valorant.domain.model.agent.role.AgentRole
import com.example.valorant.domain.state.StateListWrapper
import com.valentinilk.shimmer.Shimmer
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer

@Composable
internal fun AgentsTopBar(
    state: AgentsState,
    onRoleSelected: (AgentRole?) -> Unit,
    modifier: Modifier = Modifier,
    tonalElevation: Dp = 4.dp,
    shadowElevation: Dp = 4.dp,
    shimmer: Shimmer = rememberShimmer(ShimmerBounds.View),
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        tonalElevation = tonalElevation,
        shadowElevation = shadowElevation,
        color = MaterialTheme.colorScheme.background,
    ) {
        when (state.roles) {
            is StateListWrapper.Loading -> {
                ValorantChipGroupShimmer(
                    modifier = Modifier.padding(vertical = 4.dp),
                    shimmer = shimmer,
                )
            }
            is StateListWrapper.Success -> {
                val chipTitles = listOf("All") + state.roles.data.map { it.displayName }
                val roleIcons = state.roles.data.map { it.displayIcon }
                val selectedIndex = if (state.selectedRole == null) {
                    0
                } else {
                    state.roles.data.indexOf(state.selectedRole) + 1
                }

                ValorantChipGroupPrimary(
                    modifier = Modifier.padding(vertical = 4.dp),
                    chipTitles = chipTitles,
                    selectedChipIndex = selectedIndex,
                    onChipSelected = { index ->
                        val selectedRole = if (index == 0) null else state.roles.data.getOrNull(index - 1)
                        onRoleSelected(selectedRole)
                    },
                    roleIcons = roleIcons,
                )
            }
            is StateListWrapper.Error -> {

            }
        }
    }
}