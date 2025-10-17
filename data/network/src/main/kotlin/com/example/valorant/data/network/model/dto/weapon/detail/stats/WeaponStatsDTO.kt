package com.example.valorant.data.network.model.dto.weapon.detail.stats

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeaponStatsDTO(
    @SerialName("damageRanges")
    val damageRanges: List<DamageRangesDTO>,
)