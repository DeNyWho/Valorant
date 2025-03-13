package com.example.valorant.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "data_updates")
data class DataUpdateEntity(
    @PrimaryKey val dataType: String,
    val lastUpdatedAt: Long,
    val nextUpdateAt: Long,
)