package com.example.valorant.feature.explore.composable.tab

import androidx.compose.runtime.Composable
import com.example.valorant.domain.model.common.device.ScreenInfo
import com.example.valorant.domain.model.weapon.light.WeaponLight
import com.example.valorant.domain.state.StateListWrapper

@Composable
internal fun WeaponsTabContent(
    weapons: StateListWrapper<WeaponLight>,
    onWeaponsClick: (String) -> Unit,
    screenInfo: ScreenInfo,
) {

}