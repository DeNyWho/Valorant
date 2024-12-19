package com.example.valorant.data.network.model.dto.weapon.detail.skin

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SkinDTO(
    @SerialName("uuid")
    val uuid: String,
    @SerialName("displayName")
    val displayName: String,
    @SerialName("themeUuid")
    val themeUuid: String?,
    @SerialName("contentTierUuid")
    val contentTierUuid: String?,
    @SerialName("displayIcon")
    val displayIcon: String?,
    @SerialName("wallpaper")
    val wallpaper: String?,
    @SerialName("chromas")
    val chromas: List<ChromaDTO>?,
    @SerialName("levels")
    val levels: List<LevelDTO>?,
)