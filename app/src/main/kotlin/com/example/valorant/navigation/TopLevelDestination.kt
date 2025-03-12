package com.example.valorant.navigation

import androidx.annotation.DrawableRes
import com.example.valorant.R
import kotlin.reflect.KClass

enum class TopLevelDestination(
    @DrawableRes val icon: Int,
    val iconTextId: Int,
    val route: KClass<*>,
) {
    AGENTS(
        icon = R.drawable.valorant,
        iconTextId = R.string.navigation_bar_agents_title,
        route = ,
    ),
    MAPS(
        icon = R.drawable.maps,
        iconTextId = R.string.navigation_bar_maps_title,
        route = ,
    ),
    WEAPONS(
        icon = R.drawable.weapon,
        iconTextId = R.string.navigation_bar_weapons_title,
        route = ,
    ),
    SETTINGS(
        icon = R.drawable.settings,
        iconTextId = R.string.navigation_bar_weapons_title,
        route = ,
    )
}