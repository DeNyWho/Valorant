package com.example.valorant.core.common.util.language

import com.example.valorant.domain.model.user.LanguageType

interface LanguageManager {
    fun getDeviceLanguage(): LanguageType
}