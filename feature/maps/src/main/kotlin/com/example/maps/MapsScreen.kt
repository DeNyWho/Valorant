package com.example.maps

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.maps.component.item.CardMapGridItem
import com.example.maps.component.item.CardMapGridItemDefaults
import com.example.maps.component.item.showCardMapGridItemShimmer
import com.example.valorant.core.uikit.util.DefaultPreview
import com.example.valorant.core.uikit.util.LocalScreenInfo
import com.example.valorant.core.uikit.util.onUpdateShimmerBounds
import com.example.valorant.domain.model.common.device.ScreenType
import com.example.valorant.domain.model.map.light.MapLight
import com.example.valorant.domain.state.StateListWrapper
import com.valentinilk.shimmer.Shimmer
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer

@Composable
internal fun MapsScreen(
    viewModel: MapsViewModel = hiltViewModel(),
    onMapClick: (String) -> Unit,
) {
    val mapsState by viewModel.maps.collectAsState()

    MapsUI(
        mapsState = mapsState,
        onMapClick = onMapClick,
    )
}

@Composable
private fun MapsUI(
    mapsState: StateListWrapper<MapLight>,
    onMapClick: (String) -> Unit,
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
        if(mapsState.isLoading) {
            showCardMapGridItemShimmer(
                modifier = Modifier.size(size),
                shimmer = shimmer,
            )
        } else if(mapsState.data.isNotEmpty()) {
            items(
                mapsState.data,
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
