package com.example.valorant.domain.model.user.settings

import com.example.valorant.domain.model.common.device.FontSize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
@SerialName("user_settings")
data class UserSettings(
    @SerialName("is_first_launch")
    val isFirstLaunch: Boolean = true,

    @SerialName("font_size")
    val fontSize: FontSize = FontSize.DEFAULT
)
