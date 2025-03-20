package com.example.maps

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.valorant.domain.model.map.light.MapLight
import com.example.valorant.domain.state.StateListWrapper
import com.example.valorant.domain.usecase.map.GetMapsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class MapsViewModel @Inject constructor(
    private val getMapsUseCase: GetMapsUseCase,
): ViewModel() {
    private val _maps: MutableStateFlow<StateListWrapper<MapLight>> =
        MutableStateFlow(StateListWrapper.loading())
    val maps: StateFlow<StateListWrapper<MapLight>> =
        _maps.asStateFlow()

    init {
        loadInitialData()
    }

    private fun loadInitialData() {
        viewModelScope.launch {
            launch {
                getMaps()
            }
        }
    }

    private fun getMaps() {
        getMapsUseCase.invoke().onEach {
            _maps.value = it
        }.launchIn(viewModelScope)
    }
}