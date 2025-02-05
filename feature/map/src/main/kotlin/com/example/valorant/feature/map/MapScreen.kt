package com.example.valorant.feature.map

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.valorant.core.uikit.util.DefaultPreview

@Composable
internal fun MapScreen(
    onBackPressed: () -> Boolean,
    viewModel: MapViewModel = hiltViewModel(),
    uuid: String,
) {
    MapUI()
}

@Composable
private fun MapUI() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "Map Screen",
            style = MaterialTheme.typography.titleLarge,
        )
    }
}

@PreviewScreenSizes
@Composable
private fun PreviewMapUI() {
    DefaultPreview(true) {
        MapUI()
    }
}
