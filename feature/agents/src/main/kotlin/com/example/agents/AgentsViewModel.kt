package com.example.agents

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.agents.model.AgentsAction
import com.example.agents.model.AgentsEvent
import com.example.agents.model.AgentsState
import com.example.valorant.domain.model.agent.role.AgentRole
import com.example.valorant.domain.usecase.agent.GetAgentsRolesUseCase
import com.example.valorant.domain.usecase.agent.GetAgentsUseCase
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
internal class AgentsViewModel @Inject constructor(
    private val getAgentsUseCase: GetAgentsUseCase,
    private val getAgentsRolesUseCase: GetAgentsRolesUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(AgentsState())
    val state: StateFlow<AgentsState> = _state.asStateFlow()

    private val _action = MutableSharedFlow<AgentsAction>()
    val action: SharedFlow<AgentsAction> = _action.asSharedFlow()

    init {
        handleEvent(AgentsEvent.LoadInitialData)
    }

    fun handleEvent(event: AgentsEvent) {
        when (event) {
            AgentsEvent.LoadInitialData -> loadInitialData()
            is AgentsEvent.SelectRole -> selectRole(event.role)
            is AgentsEvent.OnAgentCardClick -> {}
        }
    }

    private fun loadInitialData() {
        viewModelScope.launch {
            launch { loadRoles() }
            launch { loadAgents(null) }
        }
    }

    private fun loadRoles() {
        getAgentsRolesUseCase.invoke()
            .onEach { rolesState ->
                _state.update { it.copy(roles = rolesState) }
            }
            .launchIn(viewModelScope)
    }

    private fun loadAgents(role: AgentRole?) {
        getAgentsUseCase.invoke(role)
            .onEach { agentsState ->
                _state.update { it.copy(agents = agentsState) }
            }
            .launchIn(viewModelScope)
    }

    private fun selectRole(role: AgentRole?) {
        _state.update { it.copy(selectedRole = role) }
        loadAgents(role)
    }

    fun navigateToAgent(agentUUID: String) = viewModelScope.launch {
        _action.emit(AgentsAction.NavigateToAgentDetail(agentUUID))
    }
}