package com.example.valorant.feature.explore.composable.grid.map

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Size
import com.example.valorant.domain.model.map.light.MapLight

@Composable
internal fun CardMapGridItem(
    modifier: Modifier = Modifier,
    map: MapLight,
    onMapClick: (String) -> Unit,
) {
    var imageWidth by remember { mutableIntStateOf(0) }
    var imageHeight by remember { mutableIntStateOf(0) }

    Card(
        modifier = modifier
            .clip(MaterialTheme.shapes.medium)
            .clickable {
                onMapClick.invoke(map.uuid)
            },
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 2.dp,
        ),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .layout { measurable, constraints ->
                    val placeable = measurable.measure(constraints)
                    imageWidth = placeable.width
                    imageHeight = placeable.height
                    layout(placeable.width, placeable.height) {
                        placeable.placeRelative(0, 0)
                    }
                }
        ) {
            if (imageWidth > 0 && imageHeight > 0) {
                AsyncImage(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.onSurfaceVariant)
                        .fillMaxSize()
                        .clip(MaterialTheme.shapes.medium),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(map.splash)
                        .crossfade(true)
                        .size(imageWidth, imageHeight)
                        .build(),
                    contentDescription = "Content thumbnail",
                    contentScale = ContentScale.Crop,
                    onError = {
                        println(it.result.throwable.message)
                    },
                )
            }

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