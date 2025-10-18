package com.example.valorant.feature.weapon.components.overview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Size
import com.example.valorant.core.uikit.component.chip.ValorantChip
import com.example.valorant.core.uikit.util.LocalScreenInfo
import com.example.valorant.domain.model.common.device.ScreenType
import com.example.valorant.domain.model.weapon.detail.WeaponDetail

@Composable
internal fun OverviewComponent(
    modifier: Modifier = Modifier,
    weapon: WeaponDetail,
) {
    val screenInfo = LocalScreenInfo.current

    val height = when (screenInfo.screenType) {
        ScreenType.SMALL -> OverviewComponentDefaults.Height.Small
        ScreenType.MEDIUM -> OverviewComponentDefaults.Height.Medium
        ScreenType.LARGE -> OverviewComponentDefaults.Height.Large
        ScreenType.EXTRA_LARGE -> OverviewComponentDefaults.Height.ExtraLarge
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
            .background(
                MaterialTheme.colorScheme.primary,
            )
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .padding(start = 16.dp, bottom = 32.dp)
                    .align(Alignment.BottomStart),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Text(
                    text = weapon.displayName,
                    style = MaterialTheme.typography.displayLarge,
                    color = Color.Black,
                )

                if (weapon.cost != null && weapon.cost != 0) {
                    ValorantChip(
                        title = weapon.cost.toString(),
                        shape = MaterialTheme.shapes.medium,
                        textStyle = MaterialTheme.typography.titleSmall,
                        textColor = MaterialTheme.colorScheme.primary,
                        icon = {
                            AsyncImage(
                                modifier = Modifier
                                    .size(12.dp),
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(com.example.valorant.core.uikit.R.drawable.credits_icon)
                                    .crossfade(true)
                                    .size(Size.ORIGINAL)
                                    .build(),
                                contentDescription = "Content thumbnail",
                                contentScale = ContentScale.Crop,
                                colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.primary),
                                onError = {
                                    println(it.result.throwable.message)
                                },
                            )
                        },
                    )
                }
            }

            weapon.categoryText?.let {
                Text(
                    modifier = Modifier
                        .padding(end = 16.dp, top = 32.dp)
                        .align(Alignment.TopEnd),
                    text = it,
                    style = MaterialTheme.typography.displayMedium,
                    color = Color.Black,
                )
            }

            AsyncImage(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize(),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(weapon.displayIcon)
                    .crossfade(true)
                    .size(Size.ORIGINAL)
                    .build(),
                contentDescription = "Content thumbnail",
                contentScale = ContentScale.Fit,
                onError = {
                    println(it.result.throwable.message)
                },
            )
        }
    }
}