package com.example.valorant.feature.explore.composable.tab

import androidx.compose.runtime.Composable
import com.example.valorant.domain.model.common.device.ScreenInfo
import com.example.valorant.domain.model.map.light.MapLight
import com.example.valorant.domain.state.StateListWrapper

@Composable
internal fun MapsTabContent(
    maps: StateListWrapper<MapLight>,
    onMapClick: (String) -> Unit,
    screenInfo: ScreenInfo,
) {

}