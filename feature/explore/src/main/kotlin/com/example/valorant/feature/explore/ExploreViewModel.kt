package com.example.valorant.feature.explore

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.valorant.domain.model.agent.light.AgentLight
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

    private val _weapons: MutableState<StateListWrapper<WeaponLight>> =
        mutableStateOf(StateListWrapper())
    val weapons: MutableState<StateListWrapper<WeaponLight>> = _weapons

    private val _maps: MutableState<StateListWrapper<MapLight>> =
        mutableStateOf(StateListWrapper())
    val maps: MutableState<StateListWrapper<MapLight>> = _maps

    init {
        observeAgents()
        observeWeapons()
        observeMaps()
    }

    private fun observeAgents() {
        getAgentsUseCase.invoke().onEach {
            _agents.value = it
        }.launchIn(viewModelScope)
    }

    private fun observeWeapons() {
        getWeaponsUseCase.invoke().onEach {
            _weapons.value = it
        }.launchIn(viewModelScope)
    }

    private fun observeMaps() {
        getMapsUseCase.invoke().onEach {
            _maps.value = it
        }.launchIn(viewModelScope)
    }
}