package com.example.valorant.navigation

import androidx.annotation.DrawableRes
import com.example.valorant.R

enum class TopLevelDestination(
    @DrawableRes val icon: Int,
    val iconTextId: Int,
) {
    EXPLORE(
        icon = com.example.valorant.core.uikit.R.drawable.valorant,
        iconTextId = R.string.navigation_bar_explore_title,
    ),
    TOURNAMENTS(
        icon = R.drawable.trophy,
        iconTextId = R.string.navigation_bar_tournaments_title,
    ),
}