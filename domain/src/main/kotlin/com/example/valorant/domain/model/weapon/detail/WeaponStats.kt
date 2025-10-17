package com.example.valorant.domain.model.weapon.detail

import com.example.valorant.domain.model.weapon.detail.stats.DamageRanges

data class WeaponStats(
    val damageRanges: List<DamageRanges>,
)