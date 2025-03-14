package com.example.valorant.data.local.model.agent

import androidx.room.Embedded
import androidx.room.Relation

data class AgentWithDetails(
    @Embedded val agent: AgentEntity,

    @Relation(
        parentColumn = "role_uuid",
        entityColumn = "uuid"
    )
    val role: AgentRoleEntity,

    @Relation(
        parentColumn = "uuid",
        entityColumn = "agent_uuid"
    )
    val ability: List<AgentAbilityEntity>,
)