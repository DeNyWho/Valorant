package com.example.valorant

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.valorant.domain.model.common.device.FontSize
import com.example.valorant.domain.model.common.device.ScreenType
import com.example.valorant.domain.usecase.settings.first_launch.FirstLaunchUseCase
import com.example.valorant.domain.usecase.settings.font.FontSizeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val fontSizeUseCase: FontSizeUseCase,
    private val firstLaunchUseCase: FirstLaunchUseCase,
) : ViewModel() {
    private val _isFirstLaunch = MutableStateFlow<Boolean?>(null)
    val isFirstLaunch: StateFlow<Boolean?> = _isFirstLaunch

    val fontSize: StateFlow<FontSize> = fontSizeUseCase.fontSize.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        FontSize.DEFAULT
    )

    init {
        viewModelScope.launch {
            firstLaunchUseCase.isFirstLaunch.collect { value ->
                _isFirstLaunch.value = value
            }
        }
    }

    fun onFirstLaunch(screenType: ScreenType) {
        viewModelScope.launch {
//            userFirstLaunchUseCase.setFirstLaunchCompleted()
            val initialFontSize = when (screenType) {
                ScreenType.SMALL -> FontSize.SMALL
                ScreenType.DEFAULT -> FontSize.DEFAULT
                ScreenType.LARGE -> FontSize.LARGE
                ScreenType.EXTRA_LARGE -> FontSize.EXTRA_LARGE
            }
            fontSizeUseCase.updateFontSize(initialFontSize)
        }
    }
}