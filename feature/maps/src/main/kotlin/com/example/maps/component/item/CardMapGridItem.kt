package com.example.maps.component.item

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Precision
import coil.size.Scale
import com.example.valorant.domain.model.map.light.MapLight

@Composable
internal fun CardMapGridItem(
    modifier: Modifier = Modifier,
    map: MapLight,
    onMapClick: (String) -> Unit,
) {
    Card(
        modifier = modifier
            .clip(MaterialTheme.shapes.medium)
            .clickable { onMapClick(map.uuid) },
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 2.dp,
        ),
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            AsyncImage(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.onSurfaceVariant)
                    .fillMaxSize()
                    .clip(MaterialTheme.shapes.medium),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(map.splash)
                    .crossfade(true)
                    .size(512, 288)
                    .scale(Scale.FIT)
                    .precision(Precision.INEXACT)
                    .build(),
                contentDescription = "Content thumbnail",
                contentScale = ContentScale.Crop,
                onError = {
                    println(it.result.throwable.message)
                },
            )

            Text(
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.BottomStart),
                text = map.displayName,
                style = MaterialTheme.typography.titleLarge,
                color = Color.White,
            )
        }
    }
}