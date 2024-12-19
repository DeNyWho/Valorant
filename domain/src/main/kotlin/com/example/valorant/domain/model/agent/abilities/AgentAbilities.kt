package com.example.valorant.domain.model.agent.abilities

data class AgentAbilities(
    val slot: AgentAbilitiesType,
    val displayName: String,
    val description: String,
    val displayIcon: String,
)