package com.example.valorant.feature.weapon

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.valorant.core.uikit.util.DefaultPreview
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
    WeaponUI()
}

@Composable
private fun WeaponUI() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "Weapon Screen",
            style = MaterialTheme.typography.titleLarge,
        )
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

@PreviewScreenSizes
@Composable
private fun PreviewWeaponUI() {
    DefaultPreview(true) {
        WeaponUI()
    }
}
