package com.example.weapons.component.item

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Size
import com.example.valorant.domain.model.weapon.light.WeaponLight

@Composable
internal fun CardWeaponGridItem(
    modifier: Modifier = Modifier,
    weapon: WeaponLight,
    onWeaponClick: (String) -> Unit,
) {
    Card(
        modifier = modifier
            .clip(MaterialTheme.shapes.medium)
            .clickable {
                onWeaponClick.invoke(weapon.uuid)
            },
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 2.dp,
        ),
    ) {
        Box(
            modifier = Modifier.background(MaterialTheme.colorScheme.onSurfaceVariant),
        ) {
            AsyncImage(
                modifier = Modifier
                    .padding(32.dp)
                    .fillMaxSize()
                    .clip(MaterialTheme.shapes.medium),
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

            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                if(!weapon.categoryText.isNullOrEmpty()) {
                    Text(
                        text = weapon.categoryText!!,
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onBackground,
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        modifier = Modifier.padding(end = 8.dp),
                        text = weapon.displayName,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onBackground,
                    )

                    if(weapon.cost != null && weapon.cost != 0) {
                        Image(
                            modifier = Modifier.size(8.dp),
                            painter = painterResource(com.example.valorant.core.uikit.R.drawable.credits_icon),
                            colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.primary),
                            contentDescription = null,
                        )
                        Text(
                            text = weapon.cost.toString(),
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.primary,
                        )
                    }
                }
            }
        }

    }
}