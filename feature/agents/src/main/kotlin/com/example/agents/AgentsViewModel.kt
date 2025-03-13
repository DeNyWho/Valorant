package com.example.agents

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.agents.model.state.AgentsUiState
import com.example.valorant.domain.model.agent.light.AgentLight
import com.example.valorant.domain.model.agent.role.AgentRole
import com.example.valorant.domain.state.StateListWrapper
import com.example.valorant.domain.usecase.agent.GetAgentsRolesUseCase
import com.example.valorant.domain.usecase.agent.GetAgentsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class AgentsViewModel @Inject constructor(
    private val getAgentsUseCase: GetAgentsUseCase,
    private val getAgentsRolesUseCase: GetAgentsRolesUseCase,
) : ViewModel() {
    private val _uiState: MutableStateFlow<AgentsUiState> =
        MutableStateFlow(AgentsUiState())
    val uiState: StateFlow<AgentsUiState> =
        _uiState.asStateFlow()

    private val _roles: MutableStateFlow<StateListWrapper<AgentRole>> =
        MutableStateFlow(StateListWrapper.loading())
    val roles: StateFlow<StateListWrapper<AgentRole>> = _roles.asStateFlow()

    private val _agents: MutableStateFlow<StateListWrapper<AgentLight>> =
        MutableStateFlow(StateListWrapper.loading())
    val agents: StateFlow<StateListWrapper<AgentLight>> = _agents.asStateFlow()

    init {
        setupAgentsFlow()
        setupAgentsRolesFlow()
    }

    private fun setupAgentsFlow() {
        viewModelScope.launch {
            _uiState
                .map { it.selectedRole }
                .distinctUntilChanged()
                .collect { selectedRole ->
                    getAgents(selectedRole)
                }
        }
    }

    private fun setupAgentsRolesFlow() {
        viewModelScope.launch {
            getAgentsRolesUseCase.invoke()
                .collect { rolesState ->
                    _roles.update { rolesState }
                }
        }
    }

    private fun getAgents(selectedRole: AgentRole?) {
        getAgentsUseCase.invoke(selectedRole)
            .onEach { stateListWrapper ->
                _agents.value = stateListWrapper
            }
            .launchIn(viewModelScope)
    }

    fun updateSelectedRole(role: AgentRole?) {
        _uiState.update { it.copy(selectedRole = role) }
    }
}