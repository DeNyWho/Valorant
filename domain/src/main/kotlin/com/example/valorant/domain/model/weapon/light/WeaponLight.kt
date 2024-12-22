package com.example.valorant.domain.model.weapon.light

data class WeaponLight(
    val uuid: String,
    val displayName: String,
    val displayIcon: String,
    val cost: Int?,
    val categoryText: String?,
)