package com.example.valorant.data.local.model.weapon

import androidx.room.Embedded
import androidx.room.Relation

data class WeaponWithDetails(
    @Embedded val weapon: WeaponEntity,

    @Relation(
        parentColumn = "uuid",
        entityColumn = "weapon_uuid"
    )
    val skins: List<WeaponSkinEntity>,

    @Relation(
        parentColumn = "uuid",
        entityColumn = "weapon_uuid"
    )
    val ranges: List<WeaponRangesEntity>,
)