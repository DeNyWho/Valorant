package com.example.valorant.feature.weapon

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.valorant.domain.usecase.weapon.GetWeaponDetailUseCase
import com.example.valorant.feature.weapon.model.WeaponAction
import com.example.valorant.feature.weapon.model.WeaponEvent
import com.example.valorant.feature.weapon.model.WeaponState
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
internal class WeaponViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getWeaponDetailUseCase: GetWeaponDetailUseCase,
): ViewModel() {
    private val weaponUUID: String = checkNotNull(savedStateHandle["weaponUUID"])

    private val _state = MutableStateFlow(WeaponState())
    val state: StateFlow<WeaponState> = _state.asStateFlow()

    private val _action = MutableSharedFlow<WeaponAction>()
    val action: SharedFlow<WeaponAction> = _action.asSharedFlow()

    init {
        handleEvent(WeaponEvent.LoadInitialData)
    }

    fun handleEvent(agentEvent: WeaponEvent) {
        when(agentEvent) {
            WeaponEvent.LoadInitialData -> loadInitialData()
            WeaponEvent.OnBack -> onBack()
        }
    }

    private fun loadInitialData() {
        viewModelScope.launch {
            launch { getWeapon(weaponUUID) }
        }
    }

    private fun getWeapon(weaponUUID: String) {
        getWeaponDetailUseCase.invoke(weaponUUID)
            .onEach { result ->
                _state.update {
                    it.copy(
                        weapon = result,
                    )
                }
            }.launchIn(viewModelScope)
    }

    private fun onBack() = viewModelScope.launch {
        _action.emit(
            WeaponAction.NavigateUp
        )
    }
}