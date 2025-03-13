package com.example.valorant.domain.model.agent.detail

import com.example.valorant.domain.model.agent.abilities.AgentAbility
import com.example.valorant.domain.model.agent.role.AgentRole

data class AgentDetail(
    val uuid: String,
    val displayName: String,
    val description: String,
    val characterTags: List<String>? = null,
    val fullPortrait: String,
    val fullPortraitV2: String,
    val background: String,
    val role: AgentRole,
    val abilities: List<AgentAbility>,
)