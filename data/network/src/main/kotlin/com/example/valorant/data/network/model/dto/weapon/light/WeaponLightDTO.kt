package com.example.valorant.data.network.model.dto.weapon.light

import com.example.valorant.data.network.model.dto.weapon.detail.stats.ShopDataDTO
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeaponLightDTO(
    @SerialName("uuid")
    val uuid: String,
    @SerialName("displayName")
    val displayName: String,
    @SerialName("displayIcon")
    val displayIcon: String,
    @SerialName("shopData")
    val shopData: ShopDataDTO?,
)