package com.example.valorant.feature.explore

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.valorant.core.uikit.component.tab.ValorantScrollableTabRow
import com.example.valorant.core.uikit.util.LocalScreenInfo
import com.example.valorant.domain.model.agent.light.AgentLight
import com.example.valorant.domain.model.common.device.ScreenInfo
import com.example.valorant.domain.model.map.light.MapLight
import com.example.valorant.domain.model.weapon.light.WeaponLight
import com.example.valorant.domain.state.StateListWrapper
import com.example.valorant.feature.explore.composable.tab.AgentsTabContent
import com.example.valorant.feature.explore.composable.tab.MapsTabContent
import com.example.valorant.feature.explore.composable.tab.WeaponsTabContent
import com.example.valorant.feature.explore.model.tab.TabItem
import kotlinx.coroutines.launch

@Composable
internal fun ExploreScreen(
    viewModel: ExploreViewModel = hiltViewModel(),
) {
    val screenInfo = LocalScreenInfo.current
    val scope = rememberCoroutineScope()

    val tabs = listOf(
        TabItem(
            titleRes = R.string.feature_explore_tab_agents_title,
            content = {
                AgentsTabContent(
                    screenInfo = screenInfo,
                    agents = viewModel.agents.value,
                    onAgentClick = { },
                )
            },
        ),
        TabItem(
            titleRes = R.string.feature_explore_tab_maps_title,
            content = {
                MapsTabContent(
                    screenInfo = screenInfo,
                    maps = viewModel.maps.value,
                    onMapClick = { },
                )
            },
        ),
        TabItem(
            titleRes = R.string.feature_explore_tab_weapons_title,
            content = {
                WeaponsTabContent(
                    screenInfo = screenInfo,
                    weapons = viewModel.weapons.value,
                    onWeaponsClick = { },
                )
            },
        ),
    )
    val pagerState = rememberPagerState(
        pageCount = { tabs.size },
    )

    Column {
        ValorantScrollableTabRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            itemModifier = Modifier.height(40.dp),
            items = tabs,
            selectedIndex = pagerState.currentPage,
            onTabSelected = { index ->
                scope.launch {
                    pagerState.animateScrollToPage(index)
                }
            },
            itemToText = { stringResource(it.titleRes) },
        )
        HorizontalPager(
            state = pagerState,
            pageSize = PageSize.Fill,
            modifier = Modifier.fillMaxSize(),
        ) { page ->
            tabs[page].content()
        }
    }
}