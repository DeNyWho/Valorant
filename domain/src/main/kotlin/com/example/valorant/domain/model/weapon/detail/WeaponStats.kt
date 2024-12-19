package com.example.valorant.domain.model.weapon.detail

import com.example.valorant.domain.model.weapon.detail.stats.AdsStats
import com.example.valorant.domain.model.weapon.detail.stats.AirBurstStats
import com.example.valorant.domain.model.weapon.detail.stats.AltShotgunStats
import com.example.valorant.domain.model.weapon.detail.stats.DamageRanges

data class WeaponStats(
    val fireRate: Double,
    val magazineSize: Int,
    val runSpeedMultiplier: Double,
    val reloadTimeSeconds: Double,
    val firstBulletAccuracy: Double,
    val shotgunPelletCount: Int,
    val wallPenetration: String,
    val fireMode: String? = null,
    val altFireType: String? = null,
    val adsStats: AdsStats,
    val altShotgunStats: AltShotgunStats? = null,
    val airBurstStats: AirBurstStats? = null,
    val damageRanges: List<DamageRanges>,
)