package com.example.valorant.data.network.model.dto.weapon.detail

import com.example.valorant.data.network.model.dto.weapon.detail.skin.SkinDTO
import com.example.valorant.data.network.model.dto.weapon.detail.stats.ShopDataDTO
import com.example.valorant.data.network.model.dto.weapon.detail.stats.WeaponStatsDTO
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeaponDetailDTO(
    @SerialName("uuid")
    val uuid: String,
    @SerialName("displayName")
    val displayName: String,
    @SerialName("displayIcon")
    val displayIcon: String,
    @SerialName("weaponStats")
    val weaponStats: WeaponStatsDTO,
    @SerialName("shopData")
    val shopData: ShopDataDTO,
    @SerialName("skins")
    val skins: List<SkinDTO>,
)