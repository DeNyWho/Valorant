package com.example.valorant.data.source.mapper.agent

import com.example.valorant.data.network.model.dto.agent.abilities.AgentAbilitiesDTO
import com.example.valorant.data.network.model.dto.agent.detail.AgentDetailDTO
import com.example.valorant.data.network.model.dto.agent.light.AgentLightDTO
import com.example.valorant.data.network.model.dto.agent.role.AgentRoleDTO
import com.example.valorant.domain.model.agent.abilities.AgentAbilities
import com.example.valorant.domain.model.agent.detail.AgentDetail
import com.example.valorant.domain.model.agent.light.AgentLight
import com.example.valorant.domain.model.agent.role.AgentRole

fun AgentLightDTO.toLight(): AgentLight = AgentLight(
    uuid = uuid,
    displayName = displayName,
    displayIcon = displayIcon,
    role = role.toRole(),
)

fun AgentDetailDTO.toDetail(): AgentDetail = AgentDetail(
    uuid = uuid,
    displayName = displayName,
    description = description,
    characterTags = characterTags,
    fullPortrait = fullPortrait,
    background = background,
    role = role.toRole(),
    abilities = abilities.map { it.toAbilities() }
)

fun AgentRoleDTO.toRole(): AgentRole = AgentRole(
    uuid = uuid,
    displayName = displayName,
    displayIcon = displayIcon,
)

fun AgentAbilitiesDTO.toAbilities(): AgentAbilities = AgentAbilities(
    slot = slot,
    displayName = displayName,
    description = description,
    displayIcon = displayIcon,
)