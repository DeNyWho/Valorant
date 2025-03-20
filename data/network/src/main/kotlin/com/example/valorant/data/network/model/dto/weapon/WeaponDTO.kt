package com.example.valorant.data.network.model.dto.weapon

import com.example.valorant.data.network.model.dto.weapon.detail.stats.ShopDataDTO
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeaponDTO(
    @SerialName("uuid")
    val uuid: String,
    @SerialName("displayName")
    val displayName: String,
    @SerialName("displayIcon")
    val displayIcon: String,
    @SerialName("shopData")
    val shopData: ShopDataDTO?,
)