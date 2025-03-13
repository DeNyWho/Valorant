package com.example.valorant.domain.model.agent.abilities

data class AgentAbility(
    val slot: AgentAbilityType,
    val displayName: String,
    val description: String,
    val displayIcon: String?,
)