package com.example.weapons

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.valorant.domain.model.agent.light.AgentLight
import com.example.valorant.domain.model.weapon.light.WeaponLight
import com.example.valorant.domain.state.StateListWrapper
import com.example.valorant.domain.usecase.map.GetMapsUseCase
import com.example.valorant.domain.usecase.weapon.GetWeaponsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class WeaponsViewModel @Inject constructor(
    private val getWeaponsUseCase: GetWeaponsUseCase,
): ViewModel() {
    private val _weapons: MutableStateFlow<StateListWrapper<WeaponLight>> =
        MutableStateFlow(StateListWrapper.loading())
    val weapons: StateFlow<StateListWrapper<WeaponLight>> =
        _weapons.asStateFlow()

    init {
        loadInitialData()
    }

    private fun loadInitialData() {
        viewModelScope.launch {
            launch {
                getWeapons()
            }
        }
    }

    private fun getWeapons() {
        getWeaponsUseCase.invoke().onEach {
            _weapons.value = it
        }.launchIn(viewModelScope)
    }
}