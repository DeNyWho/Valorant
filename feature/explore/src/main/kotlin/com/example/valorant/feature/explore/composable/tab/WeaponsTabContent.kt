package com.example.valorant.feature.explore.composable.tab

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.valorant.core.uikit.component.chip.ValorantChipGroupPrimary
import com.example.valorant.domain.model.common.device.ScreenInfo
import com.example.valorant.domain.model.common.device.ScreenType
import com.example.valorant.domain.model.weapon.light.WeaponLight
import com.example.valorant.domain.state.StateListWrapper
import com.example.valorant.feature.explore.R
import com.example.valorant.feature.explore.composable.grid.map.CardMapGridItemDefaults
import com.example.valorant.feature.explore.composable.grid.weapon.CardWeaponGridItem
import kotlinx.coroutines.launch

@Composable
internal fun WeaponsTabContent(
    weapons: StateListWrapper<WeaponLight>,
    onWeaponClick: (String) -> Unit,
    screenInfo: ScreenInfo,
    categories: List<String>,
    selectedCategory: String?,
    onCategorySelected: (String?) -> Unit,
) {
    val lazyGridState = rememberLazyGridState()
    val scope = rememberCoroutineScope()

    val size = when (screenInfo.screenType) {
        ScreenType.SMALL -> CardMapGridItemDefaults.Size.GridSmall
        ScreenType.DEFAULT -> CardMapGridItemDefaults.Size.GridMedium
        else -> CardMapGridItemDefaults.Size.GridLarge
    }
    val chipTitles = listOf(stringResource(R.string.feature_explore_tab_chip_first)) + categories
    val selectedChipIndex = when (selectedCategory) {
        null -> 0
        else -> categories.indexOf(selectedCategory) + 1
    }

    Column {
        if (weapons.isLoading) {

        } else if(weapons.data.isNotEmpty()) {
            ValorantChipGroupPrimary(
                chipTitles = chipTitles,
                selectedChipIndex = selectedChipIndex,
                onChipSelected = { selectedIndex ->
                    if (selectedIndex == 0) {
                        onCategorySelected(null)

                        scope.launch {
                            lazyGridState.animateScrollToItem(0)
                        }
                    } else {
                        onCategorySelected(categories[selectedIndex - 1])
                    }
                },
            )
        }

        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp),
            columns = GridCells.Adaptive(minSize = size),
            state = lazyGridState,
            horizontalArrangement = CardMapGridItemDefaults.HorizontalArrangement.Grid,
            verticalArrangement = CardMapGridItemDefaults.VerticalArrangement.Grid,
        ) {
            if(weapons.isLoading) {

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
}