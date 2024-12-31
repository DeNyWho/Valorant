package com.example.valorant.feature.agent

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
internal fun AgentScreen(
    viewModel: AgentViewModel = hiltViewModel(),
    uuid: String,
    onBackPressed: () -> Boolean,
) {
    LaunchedEffect(viewModel) {
        viewModel.getAgentDetail(uuid)
    }

}

