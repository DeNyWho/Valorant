package com.example.valorant.data.local.model.map

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(
    tableName = "callouts",
    foreignKeys = [
        ForeignKey(
            entity = MapEntity::class,
            parentColumns = ["uuid"],
            childColumns = ["map_uuid"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("map_uuid")]
)
data class MapCalloutEntity(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = "map_uuid")
    val mapUuid: String,
    @ColumnInfo(name = "region_name")
    val regionName: String,
    @ColumnInfo(name = "super_region_name")
    val superRegionName: String,
    @ColumnInfo(name = "location_x")
    val locationX: Double,
    @ColumnInfo(name = "location_y")
    val locationY: Double,
)