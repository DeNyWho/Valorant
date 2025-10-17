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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.valorant.core.uikit.util.LocalScreenInfo
import com.example.valorant.domain.model.common.device.ScreenType
import com.example.valorant.domain.state.StateListWrapper
import com.example.weapons.component.item.CardWeaponGridItem
import com.example.weapons.component.item.CardWeaponGridItemDefaults
import com.example.weapons.component.item.showCardWeaponGridItemShimmer
import com.example.weapons.model.WeaponsAction
import com.example.weapons.model.WeaponsEvent
import com.example.weapons.model.WeaponsState
import com.valentinilk.shimmer.Shimmer
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer

@Composable
internal fun WeaponsScreen(
    viewModel: WeaponsViewModel = hiltViewModel(),
    onWeaponClick: (String) -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val action by viewModel.action.collectAsStateWithLifecycle(initialValue = null)

    WeaponsUI(
        state = state,
        eventHandler = viewModel::handleEvent,
    )

    WeaponsActions(
        action = action,
        onMapClick = onWeaponClick,
    )
}

@Composable
private fun WeaponsUI(
    state: WeaponsState,
    eventHandler: (WeaponsEvent) -> Unit,
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
        when(state.weapons) {
            is StateListWrapper.Loading -> {
                showCardWeaponGridItemShimmer(
                    modifier = Modifier.size(size),
                    shimmer = shimmer,
                )
            }

            is StateListWrapper.Success -> {
                items(
                    state.weapons.data,
                    key = { it.uuid },
                ) { weapon ->
                    CardWeaponGridItem(
                        modifier = Modifier.size(size),
                        weapon = weapon,
                        onWeaponClick = { weaponUUID ->
                            eventHandler.invoke(WeaponsEvent.OnWeaponCardClick(weaponUUID))
                        },
                    )
                }
            }

            is StateListWrapper.Error -> {

            }
        }
    }
}

@Composable
private fun WeaponsActions(
    action: WeaponsAction?,
    onMapClick: (String) -> Unit,
) {
    LaunchedEffect(action) {
        when(action) {
            null -> Unit
            is WeaponsAction.NavigateToWeaponDetail -> onMapClick.invoke(action.weaponsUUID)
            is WeaponsAction.ShowError -> {

            }
        }
    }
}