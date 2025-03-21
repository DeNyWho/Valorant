package com.example.valorant.domain.model.user

enum class LanguageType(val code: String, val displayName: String) {
    SYSTEM("system", "System Default"),
    ARABIC("ar-AE", "العربية"),
    GERMAN("de-DE", "Deutsch"),
    ENGLISH("en-US", "English"),
    SPANISH("es-ES", "Español (España)"),
    SPANISH_MEXICO("es-MX", "Español (México)"),
    FRENCH("fr-FR", "Français"),
    INDONESIAN("id-ID", "Bahasa Indonesia"),
    ITALIAN("it-IT", "Italiano"),
    JAPANESE("ja-JP", "日本語"),
    KOREAN("ko-KR", "한국어"),
    POLISH("pl-PL", "Polski"),
    PORTUGUESE_BRAZIL("pt-BR", "Português (Brasil)"),
    RUSSIAN("ru-RU", "Русский"),
    THAI("th-TH", "ไทย"),
    TURKISH("tr-TR", "Türkçe"),
    VIETNAMESE("vi-VN", "Tiếng Việt"),
    CHINESE_SIMPLIFIED("zh-CN", "简体中文"),
    CHINESE_TRADITIONAL("zh-TW", "繁體中文");

    companion object {
        fun fromCode(code: String): LanguageType {
            return values().find { it.code == code } ?: SYSTEM
        }
    }
}