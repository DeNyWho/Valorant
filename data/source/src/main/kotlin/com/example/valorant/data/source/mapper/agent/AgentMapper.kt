package com.example.valorant.data.source.mapper.agent

import com.example.valorant.data.local.model.agent.AgentAbilityEntity
import com.example.valorant.data.local.model.agent.AgentEntity
import com.example.valorant.data.network.model.dto.agent.AgentAbilityDTO
import com.example.valorant.data.network.model.dto.agent.AgentDTO

fun AgentDTO.toEntity(): AgentEntity {
    return AgentEntity(
        uuid = uuid,
        displayName = displayName,
        displayIcon = displayIcon,
        description = description,
        fullPortrait = fullPortrait,
        fullPortraitV2 = fullPortraitV2,
        background = background,
        roleUuid = role.uuid,
        characterTags = characterTags,
    )
}

fun AgentAbilityDTO.toEntity(
    agentUuid: String,
): AgentAbilityEntity {
    return AgentAbilityEntity(
        agentUuid = agentUuid,
        slot = slot.toString(),
        displayName = displayName,
        description = description,
        displayIcon = displayIcon,
    )
}