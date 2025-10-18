package com.example.valorant.feature.weapon

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons.AutoMirrored
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.valorant.core.uikit.component.button.ValorantButtonSurface
import com.example.valorant.core.uikit.component.icon.ValorantIconPrimary
import com.example.valorant.core.uikit.util.DefaultPreview
import com.example.valorant.domain.model.weapon.detail.WeaponDetail
import com.example.valorant.domain.state.StateWrapper
import com.example.valorant.feature.weapon.components.overview.OverviewComponent
import com.example.valorant.feature.weapon.components.stats.StatsComponent
import com.example.valorant.feature.weapon.model.WeaponAction
import com.example.valorant.feature.weapon.model.WeaponEvent
import com.example.valorant.feature.weapon.model.WeaponState

@Composable
internal fun WeaponScreen(
    viewModel: WeaponViewModel = hiltViewModel(),
    onBackClick: () -> Boolean,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val action by viewModel.action.collectAsStateWithLifecycle(initialValue = null)

    WeaponContent(
        state = state,
        eventHandler = viewModel::handleEvent,
    )

    WeaponActions(
        action = action,
        onBackClick = onBackClick,
    )
}

@Composable
private fun WeaponContent(
    state: WeaponState,
    eventHandler: (WeaponEvent) -> Unit,
) {
    when(state.weapon) {
        is StateWrapper.Loading -> CircularProgressIndicator()

        is StateWrapper.Success -> {
            Box {
                Row(
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                        .align(Alignment.TopCenter)
                        .zIndex(1f)
                        .fillMaxWidth(),
                ) {
                    ValorantButtonSurface(
                        modifier = Modifier.size(32.dp),
                        paddingValues = PaddingValues(4.dp),
                        shape = MaterialTheme.shapes.small,
                        onClick = {
                            eventHandler.invoke(WeaponEvent.OnBack)
                        },
                        elevation = ButtonDefaults.elevatedButtonElevation(),
                    ) {
                        ValorantIconPrimary(
                            modifier = Modifier
                                .size(28.dp),
                            imageVector = AutoMirrored.Filled.ArrowBack,
                            contentDescription = "back",
                        )
                    }
                    Spacer(Modifier.weight(1f))
                }

                WeaponUI(
                    weapon = state.weapon.data,
                )
            }
        }

        is StateWrapper.Error -> {

        }
    }
}

@Composable
private fun WeaponUI(
    weapon: WeaponDetail,
    lazyColumnState: LazyListState = rememberLazyListState(),
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        state = lazyColumnState,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        item {
            OverviewComponent(
                weapon = weapon,
            )
        }
        item {
            StatsComponent(
                modifier = Modifier
                    .padding(horizontal = 16.dp),
                weapon = weapon,
            )
        }
    }
}

@Composable
private fun WeaponActions(
    action: WeaponAction?,
    onBackClick: () -> Boolean
) {
    LaunchedEffect(action) {
        when(action) {
            null -> Unit
            WeaponAction.NavigateUp -> onBackClick.invoke()
            is WeaponAction.ShowError -> {

            }
        }
    }
}