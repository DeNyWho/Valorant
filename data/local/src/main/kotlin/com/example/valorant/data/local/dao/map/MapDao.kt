package com.example.valorant.data.local.dao.map

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.valorant.data.local.model.map.MapEntity
import com.example.valorant.data.local.model.map.MapWithCallouts

@Dao
interface MapDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMap(map: MapEntity): Long

    @Query("SELECT * FROM maps WHERE uuid = :uuid")
    suspend fun getMapById(uuid: String): MapEntity?

    @Query("SELECT * FROM maps")
    suspend fun getAllMaps(): List<MapEntity>

    @Transaction
    @Query("SELECT * FROM maps WHERE uuid = :uuid")
    suspend fun getMapWithCallouts(uuid: String): MapWithCallouts?
}