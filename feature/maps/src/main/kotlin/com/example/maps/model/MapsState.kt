package com.example.maps.model

import androidx.compose.runtime.Immutable
import com.example.valorant.domain.model.map.light.MapLight
import com.example.valorant.domain.state.StateListWrapper

@Immutable
internal data class MapsState(
    val maps: StateListWrapper<MapLight> = StateListWrapper.loading(),
)