package com.example.valorant.feature.agent.model

import androidx.compose.runtime.Immutable
import com.example.valorant.domain.model.agent.detail.AgentDetail
import com.example.valorant.domain.state.StateWrapper

@Immutable
internal data class AgentState(
    val agent: StateWrapper<AgentDetail> = StateWrapper.loading()
)