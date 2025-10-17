package com.example.valorant.data.local.mappers.weapon

import com.example.valorant.data.local.model.weapon.WeaponEntity
import com.example.valorant.data.local.model.weapon.WeaponRangesEntity
import com.example.valorant.data.local.model.weapon.WeaponSkinEntity
import com.example.valorant.data.local.model.weapon.WeaponWithDetails
import com.example.valorant.domain.model.weapon.detail.WeaponDetail
import com.example.valorant.domain.model.weapon.detail.WeaponStats
import com.example.valorant.domain.model.weapon.detail.skin.Skin
import com.example.valorant.domain.model.weapon.detail.stats.DamageRanges
import com.example.valorant.domain.model.weapon.light.WeaponLight

fun WeaponEntity.toLight(): WeaponLight {
    return WeaponLight(
        uuid = uuid,
        displayName = displayName,
        displayIcon = displayIcon,
        categoryText = categoryText,
        cost = cost,
    )
}

fun WeaponWithDetails.toDetail(): WeaponDetail {
    return WeaponDetail(
        uuid = weapon.uuid,
        displayName = weapon.displayName,
        displayIcon = weapon.displayIcon,
        weaponStats = WeaponStats(
            damageRanges = ranges.map { it.toRanges() }
        ),
        cost = weapon.cost,
        category = weapon.category,
        categoryText = weapon.categoryText,
        skins = skins.map { it.toSkin() }
    )
}

fun WeaponRangesEntity.toRanges(): DamageRanges {
    return DamageRanges(
        rangeStartMeters = rangeStartMeters,
        rangeEndMeters = rangeEndMeters,
        headDamage = headDamage,
        bodyDamage = bodyDamage,
        legDamage = legDamage,
    )
}

fun WeaponSkinEntity.toSkin(): Skin {
    return Skin(
        uuid = uuid,
        displayName = displayName,
        displayIcon = displayIcon,
    )
}