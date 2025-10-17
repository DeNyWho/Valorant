package com.example.maps.model

import androidx.compose.runtime.Immutable

@Immutable
internal sealed interface MapsEvent {
    data object LoadInitialData: MapsEvent
    data class OnMapCardClick(val mapUUID: String): MapsEvent
}