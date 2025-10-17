package com.example.valorant.data.local.dao.weapon

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.valorant.data.local.model.weapon.WeaponEntity
import com.example.valorant.data.local.model.weapon.WeaponWithDetails
import kotlinx.coroutines.flow.Flow

@Dao
interface WeaponDao {
    @Query("SELECT * FROM weapons")
    suspend fun getAllWeapons(): List<WeaponEntity>

    @Query("SELECT * FROM weapons")
    fun getAllWeaponsFlow(): Flow<List<WeaponEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeapon(weapon: WeaponEntity)

    @Query("SELECT * FROM weapons WHERE uuid = :uuid")
    suspend fun getWeaponByUuid(uuid: String): WeaponEntity?

    @Transaction
    @Query("SELECT * FROM weapons WHERE uuid = :uuid")
    suspend fun getWeaponWithDetails(uuid: String): WeaponWithDetails?
}