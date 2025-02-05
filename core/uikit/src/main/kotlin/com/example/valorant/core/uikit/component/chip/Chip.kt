package com.example.valorant.core.uikit.component.chip

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Size
import com.example.valorant.core.uikit.util.DefaultPreview

/**
 * Valorant ChipSurface (chip with surface colors) [ValorantChipSurface].
 */
@Composable
fun ValorantChipSurface(
    modifier: Modifier = Modifier,
    title: String = "",
    shape: CornerBasedShape = MaterialTheme.shapes.large,
    textStyle: TextStyle = MaterialTheme.typography.labelSmall,
    iconUrl: String? = null,
    iconSize: Dp? = null,
) {
    Surface(
        modifier = modifier,
        shape = shape,
        contentColor = MaterialTheme.colorScheme.onSurface,
        color = MaterialTheme.colorScheme.surfaceVariant,
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 4.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            var horizontalTextPadding = 8.dp

            if(iconUrl != null && iconSize != null) {
                horizontalTextPadding = 4.dp

                AsyncImage(
                    modifier = Modifier
                        .padding(start = 4.dp)
                        .align(Alignment.CenterVertically)
                        .size(iconSize),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(iconUrl)
                        .crossfade(true)
                        .size(Size.ORIGINAL)
                        .build(),
                    contentDescription = "Content thumbnail",
                    contentScale = ContentScale.Crop,
                    onError = {
                        println(it.result.throwable.message)
                    },
                )
            }

            Text(
                text = title,
                style = textStyle,
                modifier = Modifier
                    .padding(horizontal = horizontalTextPadding),
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun PreviewValorantChipSurface() {
    DefaultPreview {
        ValorantChipSurface(
            title = "Поддержка",
        )
    }
}

/**
 * Valorant ChipPrimary (chip with primary colors) [ValorantChipPrimaryStatic].
 */
@Composable
fun ValorantChipPrimary(
    modifier: Modifier = Modifier,
    title: String = "",
    isSelected: Boolean = false,
    onSelect: ((Boolean) -> Unit)? = null,
    iconUrl: String? = null,
    iconSize: Dp? = null,
    isLarge: Boolean = false,
) {
    Surface(
        modifier = modifier,
        shape = MaterialTheme.shapes.large,
        color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
        contentColor = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onPrimaryContainer,
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 4.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            var horizontalTextPadding = 8.dp
            val textStyle = if(isLarge) MaterialTheme.typography.labelLarge else MaterialTheme.typography.labelSmall

            if(iconUrl != null && iconSize != null) {
                horizontalTextPadding = 4.dp

                AsyncImage(
                    modifier = Modifier
                        .padding(start = 4.dp)
                        .align(Alignment.CenterVertically)
                        .size(iconSize),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(iconUrl)
                        .crossfade(true)
                        .size(Size.ORIGINAL)
                        .build(),
                    contentDescription = "Content thumbnail",
                    contentScale = ContentScale.Crop,
                    onError = {
                        println(it.result.throwable.message)
                    },
                )
            }

            Text(
                text = title,
                style = textStyle,
                modifier = Modifier
                    .padding(horizontal = horizontalTextPadding)
                    .then(
                        if (onSelect != null) {
                            Modifier.clickable { onSelect(!isSelected) }
                        } else {
                            Modifier
                        }
                    )
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun PreviewValorantChipPrimary() {
    DefaultPreview {
        ValorantChipPrimary(
            title = "Поддержка",
        )
        ValorantChipPrimary(
            title = "Поддержка",
            isSelected = true,
            onSelect = { },
        )
        ValorantChipPrimary(
            title = "Поддержка",
            isSelected = false,
            onSelect = { },
        )
    }
}

@Composable
fun ValorantChipGroupPrimary(
    chipTitles: List<String>,
    selectedChipIndex: Int?,
    onChipSelected: (Int) -> Unit,
    roleIcons: List<String>? = null,
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        itemsIndexed(chipTitles) { index, title ->
            val iconUrl = if (index == 0) null else roleIcons?.getOrElse(index - 1) { null }

            ValorantChipPrimary(
                title = title,
                isSelected = selectedChipIndex == index,
                onSelect = { onChipSelected(index) },
                iconUrl = iconUrl,
                iconSize = 12.dp,
                isLarge = true,
            )
        }
    }
}