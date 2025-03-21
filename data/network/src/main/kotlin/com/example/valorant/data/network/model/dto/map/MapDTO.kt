package com.example.valorant.data.network.model.dto.map

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MapDTO(
    @SerialName("uuid")
    val uuid: String,
    @SerialName("displayName")
    val displayName: String,
    @SerialName("tacticalDescription")
    val tacticalDescription: String? = null,
    @SerialName("coordinates")
    val coordinates: String? = null,
    @SerialName("displayIcon")
    val displayIcon: String? = null,
    @SerialName("listViewIcon")
    val listViewIcon: String,
    @SerialName("splash")
    val splash: String,
    @SerialName("stylizedBackgroundImage")
    val stylizedBackgroundImage: String? = null,
    @SerialName("xMultiplier")
    val xMultiplier: Double,
    @SerialName("yMultiplier")
    val yMultiplier: Double,
    @SerialName("xScalarToAdd")
    val xScalarToAdd: Double,
    @SerialName("yScalarToAdd")
    val yScalarToAdd: Double,
    @SerialName("callouts")
    val callouts: List<CalloutDTO>? = null,
)