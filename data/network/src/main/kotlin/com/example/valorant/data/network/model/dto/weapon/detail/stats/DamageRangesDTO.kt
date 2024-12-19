package com.example.valorant.data.network.model.dto.weapon.detail.stats

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DamageRangesDTO(
    @SerialName("rangeStartMeters")
    val rangeStartMeters: Int,
    @SerialName("rangeEndMeters")
    val rangeEndMeters: Int,
    @SerialName("headDamage")
    val headDamage: Double,
    @SerialName("bodyDamage")
    val bodyDamage: Int,
    @SerialName("legDamage")
    val legDamage: Double,
)