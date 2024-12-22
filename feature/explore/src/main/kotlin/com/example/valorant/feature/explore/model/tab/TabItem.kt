package com.example.valorant.feature.explore.model.tab

import androidx.compose.runtime.Composable

internal data class TabItem(
    val titleRes: Int,
    val content: @Composable () -> Unit,
)