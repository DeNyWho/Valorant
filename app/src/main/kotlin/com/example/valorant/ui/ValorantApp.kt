package com.example.valorant.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration.Indefinite
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.example.valorant.R
import com.example.valorant.core.uikit.component.ValorantBackground
import com.example.valorant.core.uikit.component.icon.ValorantIcon
import com.example.valorant.core.uikit.component.navigation.ValorantNavigationBar
import com.example.valorant.core.uikit.component.navigation.ValorantNavigationBarItem
import com.example.valorant.navigation.ValorantNavHost

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ValorantApp(appState: ValorantAppState) {

    ValorantBackground {
        val snackbarHostState = remember { SnackbarHostState() }
        val currentDestination = appState.currentDestination

        val isOffline by appState.isOffline.collectAsStateWithLifecycle()

        val notConnectedMessage = stringResource(R.string.not_connected)

        LaunchedEffect(isOffline) {
            if (isOffline) {
                snackbarHostState.showSnackbar(
                    message = notConnectedMessage,
                    duration = Indefinite,
                )
            }
        }

        Scaffold(
            modifier = Modifier.semantics {
                testTagsAsResourceId = true
            },
            snackbarHost = { SnackbarHost(snackbarHostState) },
            contentWindowInsets = WindowInsets(0, 0, 0, 0),
        ) { padding ->
            Row(
                Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .consumeWindowInsets(padding)
                    .windowInsetsPadding(
                        WindowInsets.safeDrawing.only(
                            WindowInsetsSides.Horizontal,
                        ),
                    ),
            ) {
                Column(Modifier.fillMaxSize()) {
                    ValorantNavHost(
                        appState = appState,
                        isFirstLaunch = appState.isFirstLaunch,
                    )
                }
            }
        }
    }
}