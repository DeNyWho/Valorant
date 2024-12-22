package com.example.valorant.feature.explore.composable.grid.agent

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.ui.unit.dp

internal object CardAgentGridItemDefaults {
    object Size {
        val Min = 80.dp
        val GridSmall = 80.dp
        val GridMedium = 100.dp
        val GridLarge = 120.dp
    }

    object HorizontalArrangement {
        val Grid = Arrangement.spacedBy(12.dp)
    }

    object VerticalArrangement {
        val Grid = Arrangement.spacedBy(12.dp)
    }
}