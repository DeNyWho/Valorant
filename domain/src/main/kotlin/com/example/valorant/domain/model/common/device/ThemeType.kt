package com.example.valorant.domain.model.common.device

import com.example.valorant.domain.R

enum class ThemeType(val stringResId: Int) {
    SYSTEM(R.string.domain_theme_system),
    LIGHT(R.string.domain_theme_light),
    DARK(R.string.domain_theme_dark);
}