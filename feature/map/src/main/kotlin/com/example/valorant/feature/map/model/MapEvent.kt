package com.example.valorant.feature.map.model

import androidx.compose.runtime.Immutable

@Immutable
internal sealed interface MapEvent {
    data object LoadInitialData: MapEvent
    data object OnBack: MapEvent
}