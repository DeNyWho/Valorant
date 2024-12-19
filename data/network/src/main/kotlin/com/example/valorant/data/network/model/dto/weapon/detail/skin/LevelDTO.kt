package com.example.valorant.data.network.model.dto.weapon.detail.skin

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LevelDTO(
    @SerialName("uuid")
    val uuid: String,
    @SerialName("displayName")
    val displayName: String,
    @SerialName("levelItem")
    val levelItem: String?,
    @SerialName("displayIcon")
    val displayIcon: String?,
    @SerialName("streamedVideo")
    val streamedVideo: String?,
)