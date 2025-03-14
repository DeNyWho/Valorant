package com.example.valorant.data.network.model.dto.map

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LocationDTO(
    @SerialName("x")
    val x: Double,
    @SerialName("y")
    val y: Double,
)
