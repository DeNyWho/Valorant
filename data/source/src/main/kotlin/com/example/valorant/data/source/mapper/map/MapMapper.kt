package com.example.valorant.data.source.mapper.map

import com.example.valorant.data.network.model.dto.map.detail.CalloutDTO
import com.example.valorant.data.network.model.dto.map.detail.LocationDTO
import com.example.valorant.data.network.model.dto.map.detail.MapDetailDTO
import com.example.valorant.data.network.model.dto.map.light.MapLightDTO
import com.example.valorant.domain.model.map.detail.Callout
import com.example.valorant.domain.model.map.detail.Location
import com.example.valorant.domain.model.map.detail.MapDetail
import com.example.valorant.domain.model.map.light.MapLight

fun MapLightDTO.toLight(): MapLight = MapLight(
    uuid = uuid,
    displayName = displayName,
    splash = splash,
)

fun MapDetailDTO.toDetail(): MapDetail = MapDetail(
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
    callouts = callouts?.map { it.toCallout() },
)

fun CalloutDTO.toCallout(): Callout = Callout(
    regionName = regionName,
    superRegionName = superRegionName,
    location = location.toLocation(),
)

fun LocationDTO.toLocation(): Location = Location(
    x = x,
    y = y,
)
