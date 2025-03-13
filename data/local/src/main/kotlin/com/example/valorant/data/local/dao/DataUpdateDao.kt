package com.example.valorant.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.valorant.data.local.model.DataUpdateEntity

@Dao
interface DataUpdateDao {
    @Query("""
        SELECT CASE 
            WHEN :currentTime >= nextUpdateAt THEN 1 
            ELSE 0 
        END
        FROM data_updates
        WHERE dataType = :dataType
        LIMIT 1
    """)
    suspend fun isUpdateExpired(dataType: String, currentTime: Long = System.currentTimeMillis()): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUpdate(dataUpdate: DataUpdateEntity)

    @Query("""
        UPDATE data_updates
        SET lastUpdatedAt = nextUpdateAt, nextUpdateAt = :currentTime + :timeInterval
        WHERE dataType = :dataType
    """)
    suspend fun updateNextUpdateTime(dataType: String, currentTime: Long, timeInterval: Long = 30 * 60 * 1000)

    @Query("""
        SELECT COUNT(*) FROM data_updates WHERE dataType = :dataType
    """)
    suspend fun doesDataExist(dataType: String): Int
}