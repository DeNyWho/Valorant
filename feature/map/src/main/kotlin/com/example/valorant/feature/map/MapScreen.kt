package com.example.valorant.feature.map

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons.AutoMirrored
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.valorant.core.uikit.component.button.ValorantButtonSurface
import com.example.valorant.core.uikit.component.icon.ValorantIconPrimary
import com.example.valorant.core.uikit.component.progress.CircularProgress
import com.example.valorant.core.uikit.util.clickableWithoutRipple
import com.example.valorant.domain.model.map.detail.MapDetail
import com.example.valorant.domain.state.StateWrapper

@Composable
internal fun MapScreen(
    viewModel: MapViewModel = hiltViewModel(),
    mapId: String,
    onBackClick: () -> Boolean,
) {
    val mapState by viewModel.detailMap.collectAsState()

    LaunchedEffect(viewModel) {
        viewModel.initialize(mapId)
    }

    MapUI(
        mapState = mapState,
        onBackClick = onBackClick,
    )
}

@Composable
private fun MapUI(
    mapState: StateWrapper<MapDetail>,
    onBackClick: () -> Boolean,
) {
    when {
        mapState.isLoading -> {
            CircularProgress()
        }
        mapState.data != null -> {
            Box {
                ValorantButtonSurface(
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                        .zIndex(1f)
                        .size(32.dp),
                    paddingValues = PaddingValues(4.dp),
                    shape = MaterialTheme.shapes.small,
                    onClick = {
                        onBackClick.invoke()
                    },
                    elevation = ButtonDefaults.elevatedButtonElevation(),
                ) {
                    ValorantIconPrimary(
                        modifier = Modifier
                            .clickableWithoutRipple {
                                onBackClick.invoke()
                            }
                            .size(28.dp),
                        imageVector = AutoMirrored.Filled.ArrowBack,
                        contentDescription = "back",
                    )
                }

                MapContentUI(mapState.data!!)
            }

        }
    }
}

@Composable
private fun MapContentUI(
    map: MapDetail
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxSize(),
            model = map.splash,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            alpha = 0.8f,
        )

        val configuration = LocalConfiguration.current
        val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

        if (isLandscape) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .background(Color.White.copy(alpha = 0.7f))
                            .padding(8.dp),
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                        ) {
                            Text(
                                text = map.displayName,
                                color = Color.Black,
                                style = MaterialTheme.typography.displayMedium,
                            )

                            Text(
                                text = map.coordinates.orEmpty(),
                                color = Color.Black,
                                style = MaterialTheme.typography.displaySmall,
                            )
                        }
                    }
                }

                AsyncImage(
                    model = map.displayIcon,
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .weight(1f)
                        .heightIn(max = 300.dp)
                )
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .background(Color.White.copy(alpha = 0.7f))
                        .padding(8.dp),
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        Text(
                            text = map.displayName,
                            color = Color.Black,
                            style = MaterialTheme.typography.displayMedium,
                        )

                        Text(
                            text = map.coordinates.orEmpty(),
                            color = Color.Black,
                            style = MaterialTheme.typography.displaySmall,
                        )
                    }
                }

                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    model = map.displayIcon,
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                )
            }
        }
    }
}
