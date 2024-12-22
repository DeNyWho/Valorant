package com.example.valorant.feature.explore.composable.grid.weapon

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.ui.unit.dp

internal object CardWeaponGridItemDefaults {
    object Size {
        val GridSmall = 180.dp
        val GridMedium = 200.dp
        val GridLarge = 240.dp
    }

    object HorizontalArrangement {
        val Grid = Arrangement.spacedBy(12.dp)
    }

    object VerticalArrangement {
        val Grid = Arrangement.spacedBy(12.dp)
    }
}