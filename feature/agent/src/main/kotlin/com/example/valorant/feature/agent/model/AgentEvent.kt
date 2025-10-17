package com.example.valorant.feature.agent.model

import androidx.compose.runtime.Immutable

@Immutable
internal sealed interface AgentEvent {
    data object LoadInitialData: AgentEvent
    data object OnBack: AgentEvent
}