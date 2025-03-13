package com.example.agents

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.agents.model.state.AgentsUiState
import com.example.valorant.domain.model.agent.light.AgentLight
import com.example.valorant.domain.model.agent.role.AgentRole
import com.example.valorant.domain.state.StateListWrapper
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
) : ViewModel() {
    private val _uiState: MutableStateFlow<AgentsUiState> =
        MutableStateFlow(AgentsUiState())
    val uiState: StateFlow<AgentsUiState> =
        _uiState.asStateFlow()

    private val _agents: MutableStateFlow<StateListWrapper<AgentLight>> =
        MutableStateFlow(StateListWrapper())
    val agents: StateFlow<StateListWrapper<AgentLight>> = _agents.asStateFlow()

    private val _roles: MutableStateFlow<List<AgentRole>> =
        MutableStateFlow(emptyList())
    val roles: StateFlow<List<AgentRole>> = _roles.asStateFlow()

    private val _filteredAgents: MutableStateFlow<StateListWrapper<AgentLight>> =
        MutableStateFlow(StateListWrapper())
    val filteredAgents: StateFlow<StateListWrapper<AgentLight>> = _filteredAgents.asStateFlow()

    init {
        loadInitialData()
        setupAgentsFiltering()
    }

    private fun loadInitialData() {
        viewModelScope.launch {
            getAgents()
        }
    }

    private fun getAgents() {
        getAgentsUseCase.invoke().onEach { agentListWrapper ->
            _agents.update { agentListWrapper }

            val uniqueRoles = agentListWrapper.data
                .map { it.role }
                .distinctBy { it.uuid }

            _roles.update { uniqueRoles }

            updateFilteredAgents()
        }.launchIn(viewModelScope)
    }

    private fun setupAgentsFiltering() {
        viewModelScope.launch {
            _uiState
                .map { it.selectedRole }
                .distinctUntilChanged()
                .collect { _ ->
                    updateFilteredAgents()
                }
        }
    }

    private fun updateFilteredAgents() {
        val allAgents = _agents.value
        val selectedRole = _uiState.value.selectedRole

        _filteredAgents.value = if (selectedRole == null) {
            allAgents
        } else {
            StateListWrapper(
                data = allAgents.data.filter { it.role.uuid == selectedRole.uuid },
                isLoading = allAgents.isLoading,
                error = allAgents.error
            )
        }
    }

    fun selectRole(role: AgentRole?) {
        _uiState.update { currentState ->
            currentState.copy(selectedRole = role)
        }
    }

    fun clearRoleFilter() {
        selectRole(null)
    }
}