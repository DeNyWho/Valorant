package com.example.maps

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.maps.component.item.CardMapGridItem
import com.example.maps.component.item.CardMapGridItemDefaults
import com.example.maps.component.item.showCardMapGridItemShimmer
import com.example.maps.model.MapsAction
import com.example.maps.model.MapsEvent
import com.example.maps.model.MapsState
import com.example.valorant.core.uikit.util.LocalScreenInfo
import com.example.valorant.domain.model.common.device.ScreenType
import com.example.valorant.domain.state.StateListWrapper
import com.valentinilk.shimmer.Shimmer
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer

@Composable
internal fun MapsScreen(
    viewModel: MapsViewModel = hiltViewModel(),
    onMapClick: (String) -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val action by viewModel.action.collectAsStateWithLifecycle(initialValue = null)

    MapsUI(
        state = state,
        eventHandler = viewModel::handleEvent,
    )

    MapsAction(
        action = action,
        onMapClick = onMapClick,
    )
}

@Composable
private fun MapsUI(
    state: MapsState,
    eventHandler: (MapsEvent) -> Unit,
    shimmer: Shimmer = rememberShimmer(ShimmerBounds.View),
) {
    val lazyGridState = rememberLazyGridState()
    val screenInfo = LocalScreenInfo.current

    val size = when (screenInfo.screenType) {
        ScreenType.SMALL -> CardMapGridItemDefaults.Size.GridSmall
        ScreenType.MEDIUM -> CardMapGridItemDefaults.Size.GridMedium
        else -> CardMapGridItemDefaults.Size.GridLarge
    }

    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp),
        contentPadding = PaddingValues(
            top = 12.dp,
            bottom = 12.dp,
        ),
        columns = GridCells.Adaptive(minSize = size),
        state = lazyGridState,
        horizontalArrangement = CardMapGridItemDefaults.HorizontalArrangement.Grid,
        verticalArrangement = CardMapGridItemDefaults.VerticalArrangement.Grid,
    ) {
        when (state.maps) {
            is StateListWrapper.Loading -> {
                showCardMapGridItemShimmer(
                    modifier = Modifier.size(size),
                    shimmer = shimmer,
                )
            }

            is StateListWrapper.Success -> {
                items(
                    state.maps.data,
                    key = { it.uuid },
                ) { map ->
                    CardMapGridItem(
                        modifier = Modifier.size(size),
                        map = map,
                        onMapClick = { mapUUID ->
                            eventHandler.invoke(MapsEvent.OnMapCardClick(mapUUID))
                        },
                    )
                }
            }

            is StateListWrapper.Error -> {

            }
        }
    }
}

@Composable
private fun MapsAction(
    action: MapsAction?,
    onMapClick: (String) -> Unit,
) {
    LaunchedEffect(action) {
        when(action) {
            null -> Unit
            is MapsAction.NavigateToMapDetail -> onMapClick.invoke(action.mapUUID)
            is MapsAction.ShowError -> {

            }
        }
    }
}