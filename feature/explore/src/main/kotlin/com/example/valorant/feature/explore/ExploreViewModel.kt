package com.example.valorant.feature.explore

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.valorant.domain.model.agent.light.AgentLight
import com.example.valorant.domain.model.agent.role.AgentRole
import com.example.valorant.domain.model.map.light.MapLight
import com.example.valorant.domain.model.weapon.light.WeaponLight
import com.example.valorant.domain.state.StateListWrapper
import com.example.valorant.domain.usecase.agent.GetAgentsUseCase
import com.example.valorant.domain.usecase.map.GetMapsUseCase
import com.example.valorant.domain.usecase.weapon.GetWeaponsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
internal class ExploreViewModel @Inject constructor(
    private val getAgentsUseCase: GetAgentsUseCase,
    private val getWeaponsUseCase: GetWeaponsUseCase,
    private val getMapsUseCase: GetMapsUseCase,
): ViewModel() {

    private val _agents: MutableState<StateListWrapper<AgentLight>> =
        mutableStateOf(StateListWrapper())
    val agents: MutableState<StateListWrapper<AgentLight>> = _agents

    private val _maps: MutableState<StateListWrapper<MapLight>> =
        mutableStateOf(StateListWrapper())
    val maps: MutableState<StateListWrapper<MapLight>> = _maps

    private val _weapons: MutableState<StateListWrapper<WeaponLight>> =
        mutableStateOf(StateListWrapper())
    val weapons: MutableState<StateListWrapper<WeaponLight>> = _weapons

    private val _roles: MutableState<List<AgentRole>> = mutableStateOf(listOf())
    val roles: MutableState<List<AgentRole>> = _roles

    private val _categories: MutableState<List<String>> = mutableStateOf(listOf())
    val categories: MutableState<List<String>> = _categories

    private val _selectedRole: MutableState<AgentRole?> = mutableStateOf(null)
    val selectedRole: MutableState<AgentRole?> = _selectedRole

    private val _selectedCategory: MutableState<String?> = mutableStateOf(null)
    val selectedCategory: MutableState<String?> = _selectedCategory

    private val _selectedWeapon: MutableState<WeaponLight?> = mutableStateOf(null)
    val selectedWeapon: MutableState<WeaponLight?> = _selectedWeapon

    private val _filteredAgents: MutableState<StateListWrapper<AgentLight>> =
        mutableStateOf(StateListWrapper())
    val filteredAgents: MutableState<StateListWrapper<AgentLight>> = _filteredAgents

    private val _filteredWeapons: MutableState<StateListWrapper<WeaponLight>> =
        mutableStateOf(StateListWrapper())
    val filteredWeapons: MutableState<StateListWrapper<WeaponLight>> = _filteredWeapons

    init {
        observeAgents()
        observeMaps()
        observeWeapons()
    }

    private fun observeAgents() {
        getAgentsUseCase.invoke().onEach { state ->
            _agents.value = state

            _roles.value = state.data.map {
                it.role
            }.distinct()

            _filteredAgents.value = filterAgentsByRole(state)
        }.launchIn(viewModelScope)
    }

    private fun observeMaps() {
        getMapsUseCase.invoke().onEach { state ->
            _maps.value = state
        }.launchIn(viewModelScope)
    }

    private fun observeWeapons() {
        getWeaponsUseCase.invoke().onEach { state ->
            _weapons.value = state

            _categories.value = state.data.mapNotNull {
                it.categoryText
            }.distinct()
            _filteredWeapons.value = filterWeaponsByCategory(state)
        }.launchIn(viewModelScope)
    }

    private fun filterAgentsByRole(state: StateListWrapper<AgentLight>): StateListWrapper<AgentLight> {
        return if (_selectedRole.value == null) {
            state
        } else {
            val filteredData = state.data.filter { it.role.uuid == _selectedRole.value?.uuid }
            StateListWrapper(data = filteredData, isLoading = state.isLoading, error = state.error)
        }
    }

    private fun filterWeaponsByCategory(state: StateListWrapper<WeaponLight>): StateListWrapper<WeaponLight> {
        return if (_selectedCategory.value == null) {
            state
        } else {
            val filteredData = state.data.filter { it.categoryText == _selectedCategory.value }
            StateListWrapper(data = filteredData, isLoading = state.isLoading, error = state.error)
        }
    }

    fun selectRole(role: AgentRole?) {
        _selectedRole.value = role
        _filteredAgents.value = filterAgentsByRole(_agents.value)
    }

    fun selectCategory(category: String?) {
        _selectedCategory.value = category
        _filteredWeapons.value = filterWeaponsByCategory(_weapons.value)
    }
}