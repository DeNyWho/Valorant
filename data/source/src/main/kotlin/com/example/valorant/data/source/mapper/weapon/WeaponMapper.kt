package com.example.valorant.data.source.mapper.weapon

import com.example.valorant.data.network.model.dto.weapon.detail.WeaponDetailDTO
import com.example.valorant.data.network.model.dto.weapon.detail.skin.ChromaDTO
import com.example.valorant.data.network.model.dto.weapon.detail.skin.LevelDTO
import com.example.valorant.data.network.model.dto.weapon.detail.skin.SkinDTO
import com.example.valorant.data.network.model.dto.weapon.detail.stats.AdsStatsDTO
import com.example.valorant.data.network.model.dto.weapon.detail.stats.AirBurstStatsDTO
import com.example.valorant.data.network.model.dto.weapon.detail.stats.AltShotgunStatsDTO
import com.example.valorant.data.network.model.dto.weapon.detail.stats.DamageRangesDTO
import com.example.valorant.data.network.model.dto.weapon.detail.stats.ShopDataDTO
import com.example.valorant.data.network.model.dto.weapon.detail.stats.WeaponStatsDTO
import com.example.valorant.data.network.model.dto.weapon.light.WeaponLightDTO
import com.example.valorant.domain.model.weapon.detail.WeaponDetail
import com.example.valorant.domain.model.weapon.detail.WeaponStats
import com.example.valorant.domain.model.weapon.detail.skin.Chroma
import com.example.valorant.domain.model.weapon.detail.skin.Level
import com.example.valorant.domain.model.weapon.detail.skin.Skin
import com.example.valorant.domain.model.weapon.detail.stats.AdsStats
import com.example.valorant.domain.model.weapon.detail.stats.AirBurstStats
import com.example.valorant.domain.model.weapon.detail.stats.AltShotgunStats
import com.example.valorant.domain.model.weapon.detail.stats.DamageRanges
import com.example.valorant.domain.model.weapon.detail.stats.ShopData
import com.example.valorant.domain.model.weapon.light.WeaponLight

fun WeaponLightDTO.toLight(): WeaponLight = WeaponLight(
    uuid = uuid,
    displayName = displayName,
    displayIcon = displayIcon,
)

fun WeaponDetailDTO.toDetail(): WeaponDetail = WeaponDetail(
    uuid = uuid,
    displayName = displayName,
    displayIcon = displayIcon,
    weaponStats = weaponStats.toStats(),
    shopData = shopData.toShopData(),
    skins = skins.map { it.toSkin() },
)

fun WeaponStatsDTO.toStats(): WeaponStats = WeaponStats(
    fireRate = fireRate,
    magazineSize = magazineSize,
    runSpeedMultiplier = runSpeedMultiplier,
    reloadTimeSeconds = reloadTimeSeconds,
    firstBulletAccuracy = firstBulletAccuracy,
    shotgunPelletCount = shotgunPelletCount,
    wallPenetration = wallPenetration,
    fireMode = fireMode,
    altFireType = altFireType,
    adsStats = adsStats.toAdsStats(),
    altShotgunStats = altShotgunStats?.toShotgunStats(),
    airBurstStats = airBurstStats?.toAirBurstStats(),
    damageRanges = damageRanges.map { it.toDamageRanges() },
)

fun AdsStatsDTO.toAdsStats(): AdsStats = AdsStats(
    zoomMultiplier = zoomMultiplier,
    fireRate = fireRate,
    runSpeedMultiplier = runSpeedMultiplier,
    burstCount = burstCount,
    firstBulletAccuracy = firstBulletAccuracy,
)

fun AltShotgunStatsDTO.toShotgunStats(): AltShotgunStats = AltShotgunStats(
    shotgunPelletCount = shotgunPelletCount,
    burstRate = burstRate,
)

fun AirBurstStatsDTO.toAirBurstStats(): AirBurstStats = AirBurstStats(
    shotgunPelletCount = shotgunPelletCount,
    burstDistance = burstDistance,
)

fun DamageRangesDTO.toDamageRanges(): DamageRanges = DamageRanges(
    rangeStartMeters = rangeStartMeters,
    rangeEndMeters = rangeEndMeters,
    headDamage = headDamage,
    bodyDamage = bodyDamage,
    legDamage = legDamage,
)

fun ShopDataDTO.toShopData(): ShopData = ShopData(
    cost = cost,
    category = category,
    categoryText = categoryText,
)

fun SkinDTO.toSkin(): Skin = Skin(
    uuid = uuid,
    displayName = displayName,
    themeUuid = themeUuid,
    contentTierUuid = contentTierUuid,
    displayIcon = displayIcon,
    wallpaper = wallpaper,
    chromas = chromas?.map { it.toChroma() },
    levels = levels?.map { it.toLevel() }
)

fun ChromaDTO.toChroma(): Chroma = Chroma(
    uuid = uuid,
    displayName = displayName,
    fullRender = fullRender,
    swatch = swatch,
    streamedVideo = streamedVideo,
)

fun LevelDTO.toLevel(): Level = Level(
    uuid = uuid,
    displayName = displayName,
    levelItem = levelItem,
    displayIcon = displayIcon,
    streamedVideo = streamedVideo,
)