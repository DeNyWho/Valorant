package com.example.valorant.data.network.model.dto.weapon.detail.skin

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChromaDTO(
    @SerialName("uuid")
    val uuid: String,
    @SerialName("displayName")
    val displayName: String?,
    @SerialName("fullRender")
    val fullRender: String?,
    @SerialName("swatch")
    val swatch: String?,
    @SerialName("streamedVideo")
    val streamedVideo: String?,
)