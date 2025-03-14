package com.example.valorant.data.local.model.map

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "maps")
data class MapEntity(
    @PrimaryKey
    @ColumnInfo(name = "uuid")
    val uuid: String,
    @ColumnInfo(name = "display_name")
    val displayName: String,
    @ColumnInfo(name = "tactical_description")
    val tacticalDescription: String?,
    @ColumnInfo(name = "coordinates")
    val coordinates: String?,
    @ColumnInfo(name = "display_icon")
    val displayIcon: String?,
    @ColumnInfo(name = "list_view_icon")
    val listViewIcon: String,
    @ColumnInfo(name = "splash")
    val splash: String,
    @ColumnInfo(name = "stylized_background_image")
    val stylizedBackgroundImage: String?,
    @ColumnInfo(name = "x_multiplier")
    val xMultiplier: Double,
    @ColumnInfo(name = "y_multiplier")
    val yMultiplier: Double,
    @ColumnInfo(name = "x_scalar_to_add")
    val xScalarToAdd: Double,
    @ColumnInfo(name = "y_scalar_to_add")
    val yScalarToAdd: Double,
)