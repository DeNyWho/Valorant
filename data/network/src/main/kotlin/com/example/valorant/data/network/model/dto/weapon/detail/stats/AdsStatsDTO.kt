package com.example.valorant.data.network.model.dto.weapon.detail.stats

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AdsStatsDTO(
    @SerialName("zoomMultiplier")
    val zoomMultiplier: Double,
    @SerialName("fireRate")
    val fireRate: Double,
    @SerialName("runSpeedMultiplier")
    val runSpeedMultiplier: Double,
    @SerialName("burstCount")
    val burstCount: Int,
    @SerialName("firstBulletAccuracy")
    val firstBulletAccuracy: Double,
)