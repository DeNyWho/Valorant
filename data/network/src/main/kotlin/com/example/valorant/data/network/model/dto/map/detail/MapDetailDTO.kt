package com.example.valorant.data.network.model.dto.map.detail

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MapDetailDTO(
    @SerialName("uuid")
    val uuid: String,
    @SerialName("displayName")
    val displayName: String,
    @SerialName("tacticalDescription")
    val tacticalDescription: String,
    @SerialName("coordinates")
    val coordinates: String? = null,
    @SerialName("displayIcon")
    val displayIcon: String? = null,
    @SerialName("splash")
    val splash: String,
    @SerialName("stylizedBackgroundImage")
    val stylizedBackgroundImage: String? = null,
    @SerialName("xMultiplier")
    val xMultiplier: Double? = null,
    @SerialName("yMultiplier")
    val yMultiplier: Double? = null,
    @SerialName("xScalarToAdd")
    val xScalarToAdd: Double?,
    @SerialName("yScalarToAdd")
    val yScalarToAdd: Double?,
    @SerialName("callouts")
    val callouts: List<CalloutDTO>? = null,
)