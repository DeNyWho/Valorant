package com.example.valorant.data.source.mapper.weapon

import com.example.valorant.data.local.model.weapon.WeaponEntity
import com.example.valorant.data.network.model.dto.weapon.WeaponDTO

fun WeaponDTO.toEntity(): WeaponEntity {
    return WeaponEntity(
        uuid = uuid,
        displayName = displayName,
        displayIcon = displayIcon,
        cost = shopData?.cost,
        category = shopData?.category,
        categoryText = shopData?.categoryText,

    )
}
