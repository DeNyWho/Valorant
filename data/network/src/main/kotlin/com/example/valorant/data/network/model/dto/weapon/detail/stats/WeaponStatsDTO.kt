package com.example.valorant.data.network.model.dto.weapon.detail.stats

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeaponStatsDTO(
    @SerialName("fireRate")
    val fireRate: Double,
    @SerialName("magazineSize")
    val magazineSize: Int,
    @SerialName("runSpeedMultiplier")
    val runSpeedMultiplier: Double,
    @SerialName("equipTimeSeconds")
    val equipTimeSeconds: Double,
    @SerialName("reloadTimeSeconds")
    val reloadTimeSeconds: Double,
    @SerialName("firstBulletAccuracy")
    val firstBulletAccuracy: Double,
    @SerialName("shotgunPelletCount")
    val shotgunPelletCount: Int,
    @SerialName("wallPenetration")
    val wallPenetration: String,
    @SerialName("fireMode")
    val fireMode: String? = null,
    @SerialName("altFireType")
    val altFireType: String? = null,
    @SerialName("adsStats")
    val adsStats: AdsStatsDTO,
    @SerialName("altShotgunStats")
    val altShotgunStats: AltShotgunStatsDTO? = null,
    @SerialName("airBurstStats")
    val airBurstStats: AirBurstStatsDTO? = null,
    @SerialName("damageRanges")
    val damageRanges: List<DamageRangesDTO>,
)