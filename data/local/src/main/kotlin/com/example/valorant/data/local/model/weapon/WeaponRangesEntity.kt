package com.example.valorant.data.local.model.weapon

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "weapon_ranges",
    foreignKeys = [
        ForeignKey(
            entity = WeaponEntity::class,
            parentColumns = ["uuid"],
            childColumns = ["weapon_uuid"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("weapon_uuid")]
)
data class WeaponRangesEntity(
    @PrimaryKey
    @ColumnInfo(name = "uuid")
    val uuid: String,
    @ColumnInfo(name = "weapon_uuid")
    val weaponUuid: String,
    @ColumnInfo(name = "range_start_meters")
    val rangeStartMeters: Int,
    @ColumnInfo(name = "range_end_meters")
    val rangeEndMeters: Int,
    @ColumnInfo(name = "head_damage")
    val headDamage: Double,
    @ColumnInfo(name = "body_damage")
    val bodyDamage: Double,
    @ColumnInfo(name = "leg_damage")
    val legDamage: Double,
)