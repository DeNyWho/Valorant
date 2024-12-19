package com.example.valorant.data.network.model.dto.common

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ValorantApiDTO<T>(
    @SerialName("status")
    val status: Int,
    @SerialName("data")
    val data: T? = null,
    @SerialName("error")
    val error: String? = null,
)