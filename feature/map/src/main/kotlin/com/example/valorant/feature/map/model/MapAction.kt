package com.example.valorant.feature.map.model

import androidx.compose.runtime.Immutable

@Immutable
internal sealed interface MapAction {
    data class ShowError(val message: String): MapAction
    data object NavigateUp: MapAction
}