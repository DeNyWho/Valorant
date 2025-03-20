package com.example.valorant.data.local.model.agent

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "agent_abilities",
    foreignKeys = [
        ForeignKey(
            entity = AgentEntity::class,
            parentColumns = ["uuid"],
            childColumns = ["agent_uuid"],
            onDelete = ForeignKey.CASCADE,
        )
    ],
    indices = [Index("agent_uuid")],
    primaryKeys = ["agent_uuid", "slot", "language_code"],
)
data class AgentAbilityEntity(
    @ColumnInfo(name = "agent_uuid")
    val agentUuid: String,
    @ColumnInfo(name = "slot")
    val slot: String,
    @ColumnInfo(name = "display_name")
    val displayName: String,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo("display_icon")
    val displayIcon: String?,
    @ColumnInfo(name = "language_code")
    val languageCode: String,
)