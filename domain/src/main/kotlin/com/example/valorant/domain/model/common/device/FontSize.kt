package com.example.valorant.domain.model.common.device

import kotlinx.serialization.Serializable

@Serializable
enum class FontSize(
    val key: String,
    val fontSizeExtra: Int,
) {
    SMALL("S", -2),
    DEFAULT("M", 0),
    LARGE("L", 2),
    EXTRA_LARGE("XL", 4);
}