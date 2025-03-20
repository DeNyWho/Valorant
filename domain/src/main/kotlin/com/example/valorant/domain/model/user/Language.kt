package com.example.valorant.domain.model.user

enum class LanguageType(val code: String) {
    SYSTEM("system"),
    ARABIC("ar-AE"),
    GERMAN("de-DE"),
    ENGLISH("en-US"),
    SPANISH("es-ES"),
    SPANISH_MEXICO("es-MX"),
    FRENCH("fr-FR"),
    INDONESIAN("id-ID"),
    ITALIAN("it-IT"),
    JAPANESE("ja-JP"),
    KOREAN("ko-KR"),
    POLISH("pl-PL"),
    PORTUGUESE_BRAZIL("pt-BR"),
    RUSSIAN("ru-RU"),
    THAI("th-TH"),
    TURKISH("tr-TR"),
    VIETNAMESE("vi-VN"),
    CHINESE_SIMPLIFIED("zh-CN"),
    CHINESE_TRADITIONAL("zh-TW");

    companion object {
        fun fromCode(code: String): LanguageType {
            return values().find { it.code == code } ?: SYSTEM
        }
    }
}