package com.example.valorant.feature.weapon.components.stats

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.valorant.domain.model.weapon.detail.WeaponDetail
import com.example.valorant.feature.weapon.R

@Composable
internal fun StatsComponent(
    modifier: Modifier = Modifier,
    weapon: WeaponDetail,
) {
    Column(
        modifier = modifier
            .padding(top = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(
            text = stringResource(R.string.feature_weapon_component_stats_title),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary,
        )

        Row(
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            Text(
                text = stringResource(R.string.feature_weapon_damage_range_type_title),
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            )

            weapon.weaponStats.damageRanges.forEach {
                Text(
                    modifier = Modifier
                        .weight(1f),
                    textAlign = TextAlign.Center,
                    text = stringResource(
                        R.string.feature_weapon_damage_range_title,
                        it.rangeStartMeters,
                        it.rangeEndMeters
                    ),
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.titleSmall,
                )
            }
        }

        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth(),
            thickness = 2.dp,
            color = MaterialTheme.colorScheme.primary,
        )

        Row {
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Text(
                    text = stringResource(R.string.feature_weapon_damage_range_type_head_title),
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                )

                Text(
                    text = stringResource(R.string.feature_weapon_damage_range_type_body_title),
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                )

                Text(
                    text = stringResource(R.string.feature_weapon_damage_range_type_leg_title),
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                )
            }

            weapon.weaponStats.damageRanges.forEach { stats ->
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(bottom = 4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    Text(
                        textAlign = TextAlign.Center,
                        text = stats.headDamage.toInt().toString(),
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.titleSmall,
                    )

                    Text(
                        textAlign = TextAlign.Center,
                        text = stats.bodyDamage.toInt().toString(),
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.titleSmall,
                    )

                    Text(
                        textAlign = TextAlign.Center,
                        text = stats.legDamage.toInt().toString(),
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.titleSmall,
                    )
                }
            }
        }
    }
}