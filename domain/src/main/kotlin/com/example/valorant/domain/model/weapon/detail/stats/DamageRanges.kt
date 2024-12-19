package com.example.valorant.domain.model.weapon.detail.stats

data class DamageRanges(
    val rangeStartMeters: Int,
    val rangeEndMeters: Int,
    val headDamage: Double,
    val bodyDamage: Int,
    val legDamage: Double,
)