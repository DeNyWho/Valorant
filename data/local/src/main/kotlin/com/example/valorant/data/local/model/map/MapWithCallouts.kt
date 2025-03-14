package com.example.valorant.data.local.model.map

import androidx.room.Embedded
import androidx.room.Relation

data class MapWithCallouts(
    @Embedded val map: MapEntity,
    @Relation(
        parentColumn = "uuid",
        entityColumn = "map_uuid"
    )
    val callouts: List<MapCalloutEntity>
)
