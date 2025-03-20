package com.example.valorant.core.common.util.language

import com.example.valorant.domain.model.user.LanguageType
import java.util.Locale
import javax.inject.Inject

internal class SystemLanguageManager @Inject constructor() : LanguageManager {

    override fun getDeviceLanguage(): LanguageType {
        val locale = Locale.getDefault()
        val languageCode = "${locale.language}-${locale.country}"

        val matchedLanguage = LanguageType.entries.find { it.code == languageCode }

        return matchedLanguage ?: findClosestLanguage(locale.language) ?: LanguageType.ENGLISH
    }

    private fun findClosestLanguage(languageCode: String): LanguageType? {
        return LanguageType.entries.find {
            it != LanguageType.SYSTEM && it.code.startsWith("$languageCode-")
        }
    }
}