package com.example.valorant.core.uikit.component.icon

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.example.valorant.core.uikit.R
import com.example.valorant.core.uikit.util.DefaultPreview

@Composable
fun ValorantIcon(
    modifier: Modifier = Modifier,
    painter: Painter,
    contentDescription: String? = null,
) {
    Icon(
        modifier = modifier,
        painter = painter,
        contentDescription = contentDescription,
    )
}

@Composable
fun ValorantIconCustomTintVector(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    contentDescription: String? = null,
    tint: Color,
) {
    Icon(
        modifier = modifier,
        imageVector = imageVector,
        contentDescription = contentDescription,
        tint = tint,
    )
}

@Composable
fun ValorantIconOnSurface(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    contentDescription: String? = null,
) {
    Icon(
        imageVector,
        modifier = modifier,
        contentDescription = contentDescription,
        tint = MaterialTheme.colorScheme.onSurface,
    )
}

@PreviewLightDark
@Composable
private fun PreviewValorantIconOnSurface() {
    DefaultPreview {
        ValorantIconOnSurface(
            modifier = Modifier.size(40.dp),
            imageVector = ImageVector.vectorResource(R.drawable.valorant),
            contentDescription = "content description"
        )
    }
}

@Composable
fun ValorantIconPrimary(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    contentDescription: String? = null,
) {
    Icon(
        modifier = modifier,
        imageVector = imageVector,
        contentDescription = contentDescription,
        tint = MaterialTheme.colorScheme.primary,
    )
}

@PreviewLightDark
@Composable
private fun PreviewValorantIconPrimary() {
    DefaultPreview {
        ValorantIconPrimary(
            modifier = Modifier.size(40.dp),
            imageVector = ImageVector.vectorResource(R.drawable.valorant),
            contentDescription = "content description",
        )
    }
}

@Composable
fun ValorantIconOnPrimary(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    contentDescription: String? = null,
) {
    Icon(
        modifier = modifier,
        imageVector = imageVector,
        contentDescription = contentDescription,
        tint = MaterialTheme.colorScheme.onPrimaryContainer,
    )
}

@PreviewLightDark
@Composable
private fun PreviewValorantIconOnPrimary() {
    DefaultPreview {
        ValorantIconOnPrimary(
            modifier = Modifier.size(40.dp),
            imageVector = ImageVector.vectorResource(R.drawable.valorant),
            contentDescription = "content description",
        )
    }
}

@Composable
fun ValorantIconOnBackground(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    contentDescription: String? = null,
) {
    Icon(
        modifier = modifier,
        imageVector = imageVector,
        contentDescription = contentDescription,
        tint = MaterialTheme.colorScheme.onBackground,
    )
}

@PreviewLightDark
@Composable
private fun PreviewValorantIconOnBackground() {
    DefaultPreview {
        ValorantIconOnBackground(
            modifier = Modifier.size(40.dp),
            imageVector = ImageVector.vectorResource(R.drawable.valorant),
            contentDescription = "content description",
        )
    }
}
