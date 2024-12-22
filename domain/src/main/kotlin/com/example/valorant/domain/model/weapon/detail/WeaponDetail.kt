package com.example.valorant.domain.model.weapon.detail

import com.example.valorant.domain.model.weapon.detail.skin.Skin
import com.example.valorant.domain.model.weapon.detail.stats.ShopData

data class WeaponDetail(
    val uuid: String,
    val displayName: String,
    val displayIcon: String,
    val weaponStats: WeaponStats,
    val shopData: ShopData?,
    val skins: List<Skin>,
)