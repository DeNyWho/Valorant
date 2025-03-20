package com.example.valorant.data.local.model.weapon

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "weapons_shop_data",
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
data class WeaponShopDataEntity(
    @PrimaryKey
    @ColumnInfo(name = "weapon_uuid")
    val weaponUuid: String,
    @ColumnInfo(name = "cost")
    val cost: Int?,
    @ColumnInfo(name = "category")
    val category: String?,
    @ColumnInfo(name = "category_text")
    val categoryText: String?
)