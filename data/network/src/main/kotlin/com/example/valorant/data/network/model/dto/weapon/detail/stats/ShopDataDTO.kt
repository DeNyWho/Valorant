package com.example.valorant.data.network.model.dto.weapon.detail.stats

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ShopDataDTO(
    @SerialName("cost")
    val cost: Int,
    @SerialName("category")
    val category: String,
    @SerialName("categoryText")
    val categoryText: String,
)