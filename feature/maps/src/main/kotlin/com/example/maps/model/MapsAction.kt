package com.example.maps.model

import androidx.compose.runtime.Immutable

@Immutable
internal sealed interface MapsAction {
    data class ShowError(val message: String): MapsAction
    data class NavigateToMapDetail(val mapUUID: String): MapsAction
}