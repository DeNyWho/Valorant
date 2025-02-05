package com.example.valorant.feature.explore.composable.tab

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.valorant.domain.model.common.device.ScreenInfo
import com.example.valorant.domain.model.common.device.ScreenType
import com.example.valorant.domain.model.map.light.MapLight
import com.example.valorant.domain.state.StateListWrapper
import com.example.valorant.feature.explore.composable.grid.map.CardMapGridItem
import com.example.valorant.feature.explore.composable.grid.map.CardMapGridItemDefaults

@Composable
internal fun MapsTabContent(
    maps: StateListWrapper<MapLight>,
    onMapClick: (String) -> Unit,
    screenInfo: ScreenInfo,
) {
    val lazyGridState = rememberLazyGridState()

    val size = when (screenInfo.screenType) {
        ScreenType.SMALL -> CardMapGridItemDefaults.Size.GridSmall
        ScreenType.MEDIUM -> CardMapGridItemDefaults.Size.GridMedium
        else -> CardMapGridItemDefaults.Size.GridLarge
    }

    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp),
        columns = GridCells.Adaptive(minSize = size),
        state = lazyGridState,
        horizontalArrangement = CardMapGridItemDefaults.HorizontalArrangement.Grid,
        verticalArrangement = CardMapGridItemDefaults.VerticalArrangement.Grid,
    ) {
        if(maps.isLoading) {

        } else if(maps.data.isNotEmpty()) {
            items(
                maps.data,
                key = { it.uuid },
            ) { map ->
                CardMapGridItem(
                    modifier = Modifier.size(size),
                    map = map,
                    onMapClick = onMapClick,
                )
            }
        }
    }

}