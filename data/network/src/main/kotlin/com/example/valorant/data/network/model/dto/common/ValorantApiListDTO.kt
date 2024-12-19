package com.example.valorant.data.network.model.dto.common

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ValorantApiListDTO<T>(
    @SerialName("status")
    val status: Int,
    @SerialName("data")
    val data: List<T> = listOf(),
    @SerialName("error")
    val error: String? = null,
)