package com.example.valorant.data.network.model.dto.weapon.detail.stats

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AltShotgunStatsDTO(
    @SerialName("shotgunPelletCount")
    val shotgunPelletCount: Int,
    @SerialName("burstRate")
    val burstRate: Double,
)