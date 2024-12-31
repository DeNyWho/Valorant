package com.example.valorant.feature.agent

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.valorant.domain.model.agent.detail.AgentDetail
import com.example.valorant.domain.state.StateWrapper
import com.example.valorant.domain.usecase.agent.GetAgentDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
internal class AgentViewModel @Inject constructor(
    private val getAgentDetailUseCase: GetAgentDetailUseCase,
): ViewModel() {
    private val _agentDetail: MutableState<StateWrapper<AgentDetail>> =
        mutableStateOf(StateWrapper())
    val agentDetail: MutableState<StateWrapper<AgentDetail>> = _agentDetail

    fun getAgentDetail(uuid: String) {
        getAgentDetailUseCase.invoke(uuid).onEach {
            _agentDetail.value = it
        }.launchIn(viewModelScope)
    }

}