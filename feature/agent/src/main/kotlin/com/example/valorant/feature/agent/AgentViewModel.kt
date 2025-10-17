package com.example.valorant.feature.agent

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.valorant.domain.usecase.agent.GetAgentDetailUseCase
import com.example.valorant.feature.agent.model.AgentAction
import com.example.valorant.feature.agent.model.AgentEvent
import com.example.valorant.feature.agent.model.AgentState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class AgentViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getAgentDetailUseCase: GetAgentDetailUseCase,
): ViewModel() {
    private val agentUUID: String = checkNotNull(savedStateHandle["agentUUID"])

    private val _state = MutableStateFlow(AgentState())
    val state: StateFlow<AgentState> = _state.asStateFlow()

    private val _action = MutableSharedFlow<AgentAction>()
    val action: SharedFlow<AgentAction> = _action.asSharedFlow()

    init {
        handleEvent(AgentEvent.LoadInitialData)
    }

    fun handleEvent(agentEvent: AgentEvent) {
        when(agentEvent) {
            AgentEvent.LoadInitialData -> loadInitialData()
            AgentEvent.OnBack -> onBack()
        }
    }

    private fun loadInitialData() {
        viewModelScope.launch {
            launch { getAgent(agentUUID) }
        }
    }

    private fun getAgent(agentUUID: String) {
        getAgentDetailUseCase.invoke(agentUUID)
            .onEach { result ->
                _state.update {
                    it.copy(
                        agent = result
                    )
                }
            }.launchIn(viewModelScope)
    }


    private fun onBack() = viewModelScope.launch {
        _action.emit(
            AgentAction.NavigateUp
        )
    }
}