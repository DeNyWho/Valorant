package com.example.maps

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.maps.model.MapsAction
import com.example.maps.model.MapsEvent
import com.example.maps.model.MapsState
import com.example.valorant.domain.usecase.map.GetMapsUseCase
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
internal class MapsViewModel @Inject constructor(
    private val getMapsUseCase: GetMapsUseCase,
): ViewModel() {
    private val _state = MutableStateFlow(MapsState())
    val state: StateFlow<MapsState> = _state.asStateFlow()

    private val _action = MutableSharedFlow<MapsAction>()
    val action: SharedFlow<MapsAction> = _action.asSharedFlow()

    init {
        handleEvent(MapsEvent.LoadInitialData)
    }

    fun handleEvent(event: MapsEvent) {
        when (event) {
            MapsEvent.LoadInitialData -> loadInitialData()
            is MapsEvent.OnMapCardClick -> navigateToMap(event.mapUUID)
        }
    }

    private fun loadInitialData() {
        viewModelScope.launch {
            launch { loadMaps() }
        }
    }

    private fun loadMaps() {
        getMapsUseCase.invoke()
            .onEach { result ->
                _state.update {
                    it.copy(
                        maps = result,
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    private fun navigateToMap(mapUUID: String) = viewModelScope.launch {
        _action.emit(MapsAction.NavigateToMapDetail(mapUUID))
    }
}