package com.example.valorant.data.local.mappers.map

import com.example.valorant.data.local.model.map.MapCalloutEntity
import com.example.valorant.data.local.model.map.MapEntity
import com.example.valorant.data.local.model.map.MapWithCallouts
import com.example.valorant.domain.model.map.detail.Callout
import com.example.valorant.domain.model.map.detail.MapDetail
import com.example.valorant.domain.model.map.light.MapLight

fun MapEntity.toLight(): MapLight {
    return MapLight(
        uuid = uuid,
        displayName = displayName,
        splash = splash,
    )
}

fun MapWithCallouts.toDetail(): MapDetail {
    return MapDetail(
        uuid = map.uuid,
        displayName = map.displayName,
        tacticalDescription = map.tacticalDescription,
        coordinates = map.coordinates,
        displayIcon = map.displayIcon,
        splash = map.splash,
        stylizedBackgroundImage = map.stylizedBackgroundImage,
        xMultiplier = map.xMultiplier,
        yMultiplier = map.yMultiplier,
        xScalarToAdd = map.xScalarToAdd,
        yScalarToAdd = map.yScalarToAdd,
        callouts = callouts.map { it.toCallout() },
    )
}

fun MapCalloutEntity.toCallout(): Callout {
    return Callout(
        regionName = regionName,
        superRegionName = superRegionName,
        locationX = locationX,
        locationY = locationY,
    )
}