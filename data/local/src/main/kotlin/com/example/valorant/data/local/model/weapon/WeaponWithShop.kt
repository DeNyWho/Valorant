package com.example.valorant.data.local.model.weapon

import androidx.room.Embedded
import androidx.room.Relation

data class WeaponWithShop(
    @Embedded val weapon: WeaponEntity,
    @Relation(
        parentColumn = "uuid",
        entityColumn = "weapon_uuid"
    )
    val shopData: WeaponShopDataEntity?,
)