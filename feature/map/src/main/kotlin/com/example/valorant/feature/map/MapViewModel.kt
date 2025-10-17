package com.example.valorant.feature.map

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.valorant.domain.usecase.map.GetMapDetailUseCase
import com.example.valorant.feature.map.model.MapAction
import com.example.valorant.feature.map.model.MapEvent
import com.example.valorant.feature.map.model.MapState
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
internal class MapViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getMapDetailUseCase: GetMapDetailUseCase,
): ViewModel() {
    private val mapUUID: String = checkNotNull(savedStateHandle["mapUUID"])

    private val _state = MutableStateFlow(MapState())
    val state: StateFlow<MapState> = _state.asStateFlow()

    private val _action = MutableSharedFlow<MapAction>()
    val action: SharedFlow<MapAction> = _action.asSharedFlow()

    init {
        handleEvent(MapEvent.LoadInitialData)
    }

    fun handleEvent(mapEvent: MapEvent) {
        when(mapEvent) {
            MapEvent.LoadInitialData -> loadInitialData()
            MapEvent.OnBack -> onBack()
        }
    }

    private fun loadInitialData() {
        viewModelScope.launch {
            launch { getMap(mapUUID) }
        }
    }

    private fun getMap(mapUUID: String) {
        getMapDetailUseCase.invoke(mapUUID)
            .onEach { result ->
                _state.update {
                    it.copy(
                        map = result,
                    )
                }
            }.launchIn(viewModelScope)
    }

    private fun onBack() = viewModelScope.launch {
        _action.emit(
            MapAction.NavigateUp
        )
    }
}