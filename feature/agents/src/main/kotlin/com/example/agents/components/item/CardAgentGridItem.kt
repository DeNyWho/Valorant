package com.example.agents.components.item

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Size
import com.example.valorant.domain.model.agent.light.AgentLight

@Composable
internal fun CardAgentGridItem(
    modifier: Modifier = Modifier,
    agent: AgentLight,
    onAgentClick: (String) -> Unit,
) {
    Card(
        modifier = modifier
            .clip(MaterialTheme.shapes.medium)
            .clickable {
                onAgentClick.invoke(agent.uuid)
            },
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 2.dp,
        ),
    ) {
        AsyncImage(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.onSurfaceVariant)
                .fillMaxSize()
                .clip(MaterialTheme.shapes.medium),
            model = ImageRequest.Builder(LocalContext.current)
                .data(agent.displayIcon)
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
}