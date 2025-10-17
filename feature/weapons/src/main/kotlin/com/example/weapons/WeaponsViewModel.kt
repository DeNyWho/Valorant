package com.example.weapons

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.valorant.domain.usecase.weapon.GetWeaponsUseCase
import com.example.weapons.model.WeaponsAction
import com.example.weapons.model.WeaponsEvent
import com.example.weapons.model.WeaponsState
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
internal class WeaponsViewModel @Inject constructor(
    private val getWeaponsUseCase: GetWeaponsUseCase,
): ViewModel() {
    private val _state = MutableStateFlow(WeaponsState())
    val state: StateFlow<WeaponsState> = _state.asStateFlow()

    private val _action = MutableSharedFlow<WeaponsAction>()
    val action: SharedFlow<WeaponsAction> = _action.asSharedFlow()

    init {
        handleEvent(WeaponsEvent.LoadInitialData)
    }

    fun handleEvent(event: WeaponsEvent) {
        when (event) {
            WeaponsEvent.LoadInitialData -> loadInitialData()
            is WeaponsEvent.OnWeaponCardClick -> navigateToWeapon(event.weaponUUID)
        }
    }

    private fun loadInitialData() {
        viewModelScope.launch {
            launch { loadMaps() }
        }
    }

    private fun loadMaps() {
        getWeaponsUseCase.invoke()
            .onEach { result ->
                _state.update {
                    it.copy(
                        weapons = result,
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    private fun navigateToWeapon(mapUUID: String) = viewModelScope.launch {
        _action.emit(WeaponsAction.NavigateToWeaponDetail(mapUUID))
    }
}