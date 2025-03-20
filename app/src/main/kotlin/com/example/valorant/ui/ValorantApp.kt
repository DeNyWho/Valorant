package com.example.valorant.ui

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
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
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import com.example.agents.navigation.AgentsRoute
import com.example.maps.navigation.MapsRoute
import com.example.settings.navigation.SettingsRoute
import com.example.valorant.R
import com.example.valorant.core.uikit.component.ValorantBackground
import com.example.valorant.core.uikit.component.icon.ValorantIcon
import com.example.valorant.core.uikit.component.navigation.ValorantNavigationBar
import com.example.valorant.core.uikit.component.navigation.ValorantNavigationBarItem
import com.example.valorant.navigation.ValorantNavHost
import com.example.weapons.navigation.WeaponsRoute
import kotlin.reflect.KClass

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ValorantApp(appState: ValorantAppState) {
    val screensWithNavBar = listOf(
        AgentsRoute::class,
        MapsRoute::class,
        WeaponsRoute::class,
        SettingsRoute::class,
    )

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
            modifier = Modifier
                .semantics {
                    testTagsAsResourceId = true
                }
                .systemBarsPadding(),
            snackbarHost = { SnackbarHost(snackbarHostState) },
            contentWindowInsets = WindowInsets.safeDrawing,
            bottomBar = {
                if (currentDestination.isRouteInHierarchy(screensWithNavBar)) {
                    Surface(shadowElevation = 3.dp) {
                        ValorantNavigationBar(
                            modifier = Modifier
                                .height(60.dp)
                                .navigationBarsPadding(),
                        ) {
                            appState.topLevelDestinations.forEach { destination ->
                                ValorantNavigationBarItem(
                                    selected = currentDestination.isRouteInHierarchy(listOf(destination.route)),
                                    onClick = { appState.navigateToTopLevelDestination(destination) },
                                    icon = {
                                        ValorantIcon(
                                            modifier = Modifier.size(24.dp),
                                            painter = painterResource(destination.icon),
                                            contentDescription = stringResource(destination.iconTextId),
                                        )
                                    },
                                    label = {
                                        Text(
                                            text = stringResource(destination.iconTextId),
                                            style = MaterialTheme.typography.titleSmall,
                                        )
                                    },
                                )
                            }
                        }
                    }
                }
            }
        ) { padding ->
            ValorantNavHost(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .consumeWindowInsets(padding)
                    .windowInsetsPadding(
                        WindowInsets.safeDrawing.only(
                            WindowInsetsSides.Horizontal,
                        ),
                    ),
                appState = appState,
            )
        }
    }
}

private fun NavDestination?.isRouteInHierarchy(routes: List<KClass<*>>): Boolean =
    this?.hierarchy?.any { navDestination ->
        routes.any { route -> navDestination.hasRoute(route) }
    } ?: false