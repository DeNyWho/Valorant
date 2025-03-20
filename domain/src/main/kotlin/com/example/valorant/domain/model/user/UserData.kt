package com.example.valorant.domain.model.user

import com.example.valorant.domain.model.common.device.FontSize
import com.example.valorant.domain.model.common.device.ThemeType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("user_data")
data class UserData(
    @SerialName("is_first_launch")
    val isFirstLaunch: Boolean = true,

    @SerialName("font_size")
    val fontSize: FontSize = FontSize.DEFAULT,

    @SerialName("theme")
    val theme: ThemeType = ThemeType.SYSTEM,

    @SerialName("language")
    val language: LanguageType = LanguageType.SYSTEM,
)
