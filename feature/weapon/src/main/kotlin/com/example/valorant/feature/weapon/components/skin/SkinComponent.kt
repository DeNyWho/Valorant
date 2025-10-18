package com.example.valorant.feature.weapon.components.skin

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.valorant.core.uikit.util.LocalScreenInfo
import com.example.valorant.domain.model.common.device.ScreenType
import com.example.valorant.domain.model.weapon.detail.WeaponDetail
import kotlinx.coroutines.launch

@Composable
internal fun SkinComponent(
    modifier: Modifier = Modifier,
    weapon: WeaponDetail,
) {
    val pagerState = rememberPagerState(
        pageCount = { weapon.skins.size },
    )
    val coroutineScope = rememberCoroutineScope()
    val lazyListState = rememberLazyListState()
    val screenInfo = LocalScreenInfo.current

    val pagerHeight = when (screenInfo.screenType) {
        ScreenType.SMALL -> 200.dp
        ScreenType.MEDIUM -> 280.dp
        ScreenType.LARGE -> 350.dp
        ScreenType.EXTRA_LARGE -> 400.dp
    }

    val thumbHeight = when (screenInfo.screenType) {
        ScreenType.SMALL -> 70.dp
        ScreenType.MEDIUM -> 90.dp
        ScreenType.LARGE -> 110.dp
        ScreenType.EXTRA_LARGE -> 130.dp
    }

    LaunchedEffect(pagerState.currentPage) {
        lazyListState.animateScrollToItem(pagerState.currentPage)
    }

    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .height(thumbHeight),
        state = lazyListState,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(horizontal = 0.dp),
    ) {
        itemsIndexed(weapon.skins) { index, skin ->
            Box(
                modifier = Modifier
                    .size(thumbHeight)
                    .clip(RoundedCornerShape(8.dp))
                    .background(
                        color = if (pagerState.currentPage == index)
                            MaterialTheme.colorScheme.primary
                        else
                            MaterialTheme.colorScheme.surfaceVariant
                    )
                    .clickable {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    }
                    .padding(4.dp),
                contentAlignment = Alignment.Center,
            ) {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(MaterialTheme.shapes.medium),
                    model = skin.displayIcon,
                    contentDescription = skin.displayName,
                    contentScale = ContentScale.Fit,
                )
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = MaterialTheme.shapes.medium)
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 24.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        HorizontalPager(
            modifier = Modifier
                .fillMaxWidth()
                .height(pagerHeight),
            state = pagerState,
            verticalAlignment = Alignment.CenterVertically,
        ) { page ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        shape = MaterialTheme.shapes.medium,
                    ),
                contentAlignment = Alignment.Center,
            ) {
                AsyncImage(
                    modifier = Modifier.fillMaxSize(0.8f),
                    model = weapon.skins[page].displayIcon,
                    contentDescription = weapon.skins[page].displayName,
                    contentScale = ContentScale.Fit,
                )
            }
        }

        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            text = weapon.skins[pagerState.currentPage].displayName,
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold,
        )
    }
}