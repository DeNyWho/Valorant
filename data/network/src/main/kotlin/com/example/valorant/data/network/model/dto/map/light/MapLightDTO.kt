package com.example.valorant.data.network.model.dto.map.light

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MapLightDTO(
    @SerialName("uuid")
    val uuid: String,
    @SerialName("displayName")
    val displayName: String,
    @SerialName("splash")
    val splash: String? = null,
)