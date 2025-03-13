package com.example.agents.components.item

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.ui.unit.dp

internal object CardAgentGridItemDefaults {
    object Size {
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