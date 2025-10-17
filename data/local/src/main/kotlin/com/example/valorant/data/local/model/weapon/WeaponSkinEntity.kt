package com.example.valorant.data.local.model.weapon

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "weapon_skins",
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
data class WeaponSkinEntity(
    @PrimaryKey
    @ColumnInfo(name = "uuid")
    val uuid: String,
    @ColumnInfo(name = "weapon_uuid")
    val weaponUuid: String,
    @ColumnInfo(name = "display_name")
    val displayName: String,
    @ColumnInfo(name = "display_icon")
    val displayIcon: String? = null,
)