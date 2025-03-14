package com.example.valorant.data.source.mapper.map

import com.example.valorant.data.local.model.map.MapCalloutEntity
import com.example.valorant.data.local.model.map.MapEntity
import com.example.valorant.data.network.model.dto.map.MapDTO

fun MapDTO.toCalloutEntities(): List<MapCalloutEntity>? {
    return callouts?.map { callout ->
        MapCalloutEntity(
            mapUuid = uuid,
            regionName = callout.regionName,
            superRegionName = callout.superRegionName,
            locationX = callout.location.x,
            locationY = callout.location.y
        )
    }
}

fun MapDTO.toEntity(): MapEntity {
    return MapEntity(
        uuid = uuid,
        displayName = displayName,
        tacticalDescription = tacticalDescription,
        coordinates = coordinates,
        displayIcon = displayIcon,
        splash = splash,
        stylizedBackgroundImage = stylizedBackgroundImage,
        xMultiplier = xMultiplier,
        yMultiplier = yMultiplier,
        xScalarToAdd = xScalarToAdd,
        yScalarToAdd = yScalarToAdd,
    )
}