package com.example.weapons

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.valorant.core.uikit.util.LocalScreenInfo
import com.example.valorant.domain.model.common.device.ScreenType
import com.example.valorant.domain.model.weapon.light.WeaponLight
import com.example.valorant.domain.state.StateListWrapper
import com.example.weapons.component.item.CardWeaponGridItem
import com.example.weapons.component.item.CardWeaponGridItemDefaults
import com.example.weapons.component.item.showCardWeaponGridItemShimmer
import com.valentinilk.shimmer.Shimmer
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer

@Composable
internal fun WeaponsScreen(
    viewModel: WeaponsViewModel = hiltViewModel(),
    onWeaponClick: (String) -> Unit,
) {
    val weaponsState by viewModel.weapons.collectAsState()

    WeaponsUI(
        weapons = weaponsState,
        onWeaponClick = onWeaponClick,
    )
}

@Composable
private fun WeaponsUI(
    weapons: StateListWrapper<WeaponLight>,
    onWeaponClick: (String) -> Unit,
    shimmer: Shimmer = rememberShimmer(ShimmerBounds.View),
) {
    val lazyGridState = rememberLazyGridState()
    val screenInfo = LocalScreenInfo.current

    val size = when (screenInfo.screenType) {
        ScreenType.SMALL -> CardWeaponGridItemDefaults.Size.GridSmall
        ScreenType.MEDIUM -> CardWeaponGridItemDefaults.Size.GridMedium
        else -> CardWeaponGridItemDefaults.Size.GridLarge
    }

    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp),
        contentPadding = PaddingValues(
            top = 12.dp,
            bottom = 12.dp,
        ),
        columns = GridCells.Adaptive(minSize = size),
        state = lazyGridState,
        horizontalArrangement = CardWeaponGridItemDefaults.HorizontalArrangement.Grid,
        verticalArrangement = CardWeaponGridItemDefaults.VerticalArrangement.Grid,
    ) {
        if(weapons.isLoading) {
            showCardWeaponGridItemShimmer(
                modifier = Modifier.size(size),
                shimmer = shimmer,
            )
        } else if(weapons.data.isNotEmpty()) {
            items(
                weapons.data,
                key = { it.uuid },
            ) { weapon ->
                CardWeaponGridItem(
                    modifier = Modifier.size(size),
                    weapon = weapon,
                    onWeaponClick = onWeaponClick,
                )
            }
        }
    }
}