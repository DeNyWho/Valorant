package com.example.valorant.domain.model.common.device

data class ScreenInfo(
    val screenType: ScreenType = ScreenType.MEDIUM,
    val fontSizePrefs: FontSize = FontSize.DEFAULT,
    val portraitWidthDp: Int = 400,
    val portraitHeightDp: Int = 800,
)