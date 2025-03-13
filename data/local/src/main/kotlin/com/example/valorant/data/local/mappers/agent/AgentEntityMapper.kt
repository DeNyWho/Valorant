package com.example.valorant.data.local.mappers.agent

import com.example.valorant.data.local.model.agent.AgentAbilityEntity
import com.example.valorant.data.local.model.agent.AgentEntity
import com.example.valorant.data.local.model.agent.AgentRoleEntity
import com.example.valorant.data.local.model.agent.AgentWithDetails
import com.example.valorant.domain.model.agent.abilities.AgentAbility
import com.example.valorant.domain.model.agent.abilities.AgentAbilityType
import com.example.valorant.domain.model.agent.detail.AgentDetail
import com.example.valorant.domain.model.agent.light.AgentLight
import com.example.valorant.domain.model.agent.role.AgentRole

fun AgentEntity.toLight(): AgentLight {
    return AgentLight(
        uuid = uuid,
        displayName = displayName,
        displayIcon = displayIcon,
    )
}

fun AgentWithDetails.toDetail(): AgentDetail {
    return AgentDetail(
        uuid = agent.uuid,
        displayName = agent.displayName,
        description = agent.description,
        characterTags = agent.characterTags ?: emptyList(),
        fullPortrait = agent.fullPortrait,
        fullPortraitV2 = agent.fullPortraitV2,
        background = agent.background,
        role = role.toRole(),
        abilities = ability.map { it.toAbility() }
    )
}

fun AgentRoleEntity.toRole(): AgentRole {
    return AgentRole(
        uuid = uuid,
        displayName = displayName,
        displayIcon = displayIcon
    )
}

fun AgentAbilityEntity.toAbility(): AgentAbility {
    return AgentAbility(
        slot = parseAbilitySlot(slot),
        displayName = displayName,
        description = description,
        displayIcon = displayIcon
    )
}

private fun parseAbilitySlot(slot: String): AgentAbilityType {
    return when (slot) {
        "Ability1" -> AgentAbilityType.Ability1
        "Ability2" -> AgentAbilityType.Ability2
        "Grenade" -> AgentAbilityType.Grenade
        "Ultimate" -> AgentAbilityType.Ultimate
        "Passive" -> AgentAbilityType.Passive
        else -> AgentAbilityType.Ability1
    }
}