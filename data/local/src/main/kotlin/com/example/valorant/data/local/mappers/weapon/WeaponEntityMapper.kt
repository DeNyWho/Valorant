package com.example.valorant.data.local.mappers.weapon

import com.example.valorant.data.local.model.weapon.WeaponWithShop
import com.example.valorant.domain.model.weapon.light.WeaponLight

fun WeaponWithShop.toLight(): WeaponLight {
    return WeaponLight(
        uuid = weapon.uuid,
        displayName = weapon.displayName,
        displayIcon = weapon.displayIcon,
        categoryText = shopData?.categoryText,
        cost = shopData?.cost,
    )
}
