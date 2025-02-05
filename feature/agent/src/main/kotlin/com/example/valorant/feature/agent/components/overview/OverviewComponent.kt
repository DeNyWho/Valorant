package com.example.valorant.feature.agent.components.overview

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import com.example.valorant.core.uikit.component.chip.ValorantChipSurface
import com.example.valorant.core.uikit.util.LocalScreenInfo
import com.example.valorant.domain.model.agent.detail.AgentDetail
import com.example.valorant.domain.model.common.device.ScreenType

@Composable
internal fun OverviewComponent(
    modifier: Modifier = Modifier,
    agent: AgentDetail,
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
            Column (
                modifier = Modifier
                    .padding(start = 16.dp, bottom = 32.dp)
                    .align(Alignment.BottomStart),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Text(
                    text = agent.displayName,
                    style = MaterialTheme.typography.displayLarge,
                    color = Color.Black,
                )
                ValorantChipSurface(
                    title = agent.role.displayName,
                    shape = MaterialTheme.shapes.medium,
                    textStyle = MaterialTheme.typography.titleSmall,
                    iconUrl = agent.role.displayIcon,
                    iconSize = 16.dp,
                )
            }

            AsyncImage(
                model = agent.background,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .zIndex(-1f)
            )

            Row {
                Spacer(modifier = Modifier.weight(1f))

                Box(
                    modifier = Modifier.weight(2f),
                ) {
                    AsyncImage(
                        model = agent.fullPortraitV2,
                        contentDescription = null,
                        contentScale = ContentScale.FillHeight,
                        modifier = Modifier
                            .fillMaxHeight()
                            .aspectRatio(9f / 16f),
                    )
                }
            }
        }

    }
}