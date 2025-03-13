package com.example.valorant.data.local.model.agent

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "agent_abilities",
    foreignKeys = [
        ForeignKey(
            entity = AgentEntity::class,
            parentColumns = ["uuid"],
            childColumns = ["agentUuid"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("agentUuid")],
    primaryKeys = ["agentUuid", "slot"],
)
data class AgentAbilityEntity(
    val agentUuid: String,
    val slot: String,
    val displayName: String,
    val description: String,
    val displayIcon: String?
)