package com.example.valorant.feature.explore

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
internal fun ExploreScreen(
    viewModel: ExploreViewModel = hiltViewModel(),
) {
    ExploreUI()
}

@Composable
private fun ExploreUI() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "Explore Screen",
            style = MaterialTheme.typography.titleLarge,
        )
    }
}

@PreviewScreenSizes
@Composable
private fun PreviewExploreUI() {
    DefaultPreview(true) {
        ExploreUI()
    }
}
