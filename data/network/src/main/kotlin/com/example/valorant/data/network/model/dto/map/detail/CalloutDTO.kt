package com.example.valorant.data.network.model.dto.map.detail

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CalloutDTO(
    @SerialName("regionName")
    val regionName: String,
    @SerialName("superRegionName")
    val superRegionName: String,
    @SerialName("location")
    val location: LocationDTO,
)