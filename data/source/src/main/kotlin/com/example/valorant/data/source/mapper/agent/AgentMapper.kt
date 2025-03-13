package com.example.valorant.data.source.mapper.agent

import com.example.valorant.data.local.model.agent.AgentAbilityEntity
import com.example.valorant.data.local.model.agent.AgentEntity
import com.example.valorant.data.local.model.agent.AgentRoleEntity
import com.example.valorant.data.network.model.dto.agent.abilities.AgentAbilitiesDTO
import com.example.valorant.data.network.model.dto.agent.AgentDTO
import com.example.valorant.data.network.model.dto.agent.light.AgentLightDTO
import com.example.valorant.data.network.model.dto.agent.role.AgentRoleDTO
import com.example.valorant.domain.model.agent.abilities.AgentAbility
import com.example.valorant.domain.model.agent.detail.AgentDetail
import com.example.valorant.domain.model.agent.light.AgentLight
import com.example.valorant.domain.model.agent.role.AgentRole

fun AgentLightDTO.toLight(): AgentLight = AgentLight(
    uuid = uuid,
    displayName = displayName,
    displayIcon = displayIcon,
)

fun AgentDTO.toDetail(): AgentDetail = AgentDetail(
    uuid = uuid,
    displayName = displayName,
    description = description,
    characterTags = characterTags,
    fullPortrait = fullPortrait,
    fullPortraitV2 = fullPortraitV2,
    background = background,
    role = role.toRole(),
    abilities = abilities.map { it.toAbilities() }
)

fun AgentRoleDTO.toRole(): AgentRole = AgentRole(
    uuid = uuid,
    displayName = displayName,
    displayIcon = displayIcon,
)

fun AgentAbilitiesDTO.toAbilities(): AgentAbility = AgentAbility(
    slot = slot,
    displayName = displayName,
    description = description,
    displayIcon = displayIcon,
)

fun AgentDTO.toEntity() = AgentEntity(
    uuid = uuid,
    displayName = displayName,
    displayIcon = displayIcon,
    description = description,
    characterTags = characterTags,
    fullPortrait = fullPortrait,
    fullPortraitV2 = fullPortraitV2,
    background = background,
    roleUuid = role.uuid
)

fun AgentRoleDTO.toEntity() = AgentRoleEntity(
    uuid = uuid,
    displayName = displayName,
    displayIcon = displayIcon
)

fun AgentAbilitiesDTO.toEntity(agentUuid: String) = AgentAbilityEntity(
    agentUuid = agentUuid,
    slot = slot.name,
    displayName = displayName,
    description = description,
    displayIcon = displayIcon
)