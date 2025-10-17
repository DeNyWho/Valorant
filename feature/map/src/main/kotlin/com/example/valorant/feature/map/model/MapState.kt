package com.example.valorant.feature.map.model

import androidx.compose.runtime.Immutable
import com.example.valorant.domain.model.map.detail.MapDetail
import com.example.valorant.domain.state.StateWrapper

@Immutable
internal data class MapState(
    val map: StateWrapper<MapDetail> = StateWrapper.loading()
)