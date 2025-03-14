package com.example.valorant.domain.model.map.detail

data class MapDetail(
    val uuid: String,
    val displayName: String,
    val tacticalDescription: String?,
    val coordinates: String? = null,
    val displayIcon: String? = null,
    val splash: String,
    val stylizedBackgroundImage: String? = null,
    val xMultiplier: Double?,
    val yMultiplier: Double?,
    val xScalarToAdd: Double?,
    val yScalarToAdd: Double?,
    val callouts: List<Callout>?,
)