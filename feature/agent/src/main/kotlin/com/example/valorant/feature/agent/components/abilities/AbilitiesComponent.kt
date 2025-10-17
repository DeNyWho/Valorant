package com.example.valorant.feature.agent.components.abilities

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.valorant.domain.model.agent.abilities.AgentAbility
import com.example.valorant.feature.agent.R
import kotlinx.coroutines.launch

@Composable
internal fun AbilitiesComponent(
    modifier: Modifier = Modifier,
    abilities: List<AgentAbility>,
) {
    val pagerState = rememberPagerState(
        pageCount = { abilities.size },
    )
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(
            text = stringResource(R.string.feature_agent_component_ability_title),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground,
        )

        TabRow(
            selectedTabIndex = pagerState.currentPage,
            containerColor = MaterialTheme.colorScheme.background,
            indicator = { _ ->
                Box(
                    modifier = Modifier
                        .height(4.dp)
                        .background(Color.Transparent),
                )
            },
            divider = {}
        ) {
            abilities.forEachIndexed { index, tabItem ->
                if(!tabItem.displayIcon.isNullOrEmpty()) {
                Tab(
                    selected = pagerState.currentPage == index,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                ) {
                    IconTab(
                        ability = tabItem,
                        isSelected = pagerState.currentPage == index
                    )
                }
                    }
            }
        }

        var contentHeight by remember { mutableStateOf(0.dp) }

        val updateHeight: (Int) -> Unit = { newHeight ->
            if (newHeight.dp > contentHeight) {
                contentHeight = newHeight.dp
            }
        }

        HorizontalPager(
            pageSize = PageSize.Fill,
            state = pagerState,
        ) { page ->
            Box(
                modifier = Modifier.height(contentHeight.coerceAtLeast(100.dp)),
            ) {
                Column(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .onSizeChanged { size ->
                            updateHeight(size.height)
                        },
                ) {
                    Text(
                        text = abilities[page].displayName,
                        style = MaterialTheme.typography.titleMedium,
                    )

                    Text(
                        text = abilities[page].description,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            }
        }
    }
}

@Composable
fun IconTab(ability: AgentAbility, isSelected: Boolean) {
    Box(
        modifier = Modifier
            .size(48.dp)
            .background(
                color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground,
                shape = MaterialTheme.shapes.medium,
            )
            .border(
                width = 1.dp,
                shape = MaterialTheme.shapes.medium,
                color = MaterialTheme.colorScheme.onBackground,
            ),
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            modifier = Modifier
                .size(32.dp),
            model = ability.displayIcon,
            contentDescription = null,
            colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.background),
            contentScale = ContentScale.Fit,
        )
    }
}
