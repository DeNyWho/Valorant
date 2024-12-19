package com.example.valorant.domain.model.weapon.detail.skin

data class Skin(
    val uuid: String,
    val displayName: String,
    val themeUuid: String?,
    val contentTierUuid: String?,
    val displayIcon: String?,
    val wallpaper: String?,
    val chromas: List<Chroma>?,
    val levels: List<Level>?,
)