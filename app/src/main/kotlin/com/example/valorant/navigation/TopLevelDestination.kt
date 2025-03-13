package com.example.valorant.navigation

import androidx.annotation.DrawableRes
import com.example.agents.navigation.AgentsRoute
import com.example.maps.navigation.MapsRoute
import com.example.settings.navigation.SettingsRoute
import com.example.valorant.R
import com.example.weapons.navigation.WeaponsRoute
import kotlin.reflect.KClass

enum class TopLevelDestination(
    @DrawableRes val icon: Int,
    val iconTextId: Int,
    val route: KClass<*>,
) {
    AGENTS(
        icon = R.drawable.valorant,
        iconTextId = R.string.navigation_bar_agents_title,
        route = AgentsRoute::class,
    ),
    MAPS(
        icon = R.drawable.maps,
        iconTextId = R.string.navigation_bar_maps_title,
        route = MapsRoute::class,
    ),
    WEAPONS(
        icon = R.drawable.weapon,
        iconTextId = R.string.navigation_bar_weapons_title,
        route = WeaponsRoute::class,
    ),
    SETTINGS(
        icon = R.drawable.settings,
        iconTextId = R.string.navigation_bar_settings_title,
        route = SettingsRoute::class,
    )
}