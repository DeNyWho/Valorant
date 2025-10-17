package com.example.valorant.data.local.model.weapon

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weapons")
data class WeaponEntity(
    @PrimaryKey
    val uuid: String,
    @ColumnInfo(name = "display_name")
    val displayName: String,
    @ColumnInfo(name = "display_icon")
    val displayIcon: String,
    @ColumnInfo(name = "cost")
    val cost: Int?,
    @ColumnInfo(name = "category")
    val category: String?,
    @ColumnInfo(name = "category_text")
    val categoryText: String?,

)