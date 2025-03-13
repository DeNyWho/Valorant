package com.example.valorant

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.valorant.domain.model.common.device.FontSize
import com.example.valorant.domain.model.common.device.ScreenType
import com.example.valorant.domain.model.common.device.ThemeType
import com.example.valorant.domain.usecase.settings.first_launch.FirstLaunchUseCase
import com.example.valorant.domain.usecase.settings.font.FontSizeUseCase
import com.example.valorant.domain.usecase.settings.theme.ThemeSettingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val fontSizeUseCase: FontSizeUseCase,
    private val firstLaunchUseCase: FirstLaunchUseCase,
    private val themeSettingsUseCase: ThemeSettingsUseCase,
) : ViewModel() {
    private val _isFirstLaunch = MutableStateFlow<Boolean?>(null)
    val isFirstLaunch: StateFlow<Boolean?> = _isFirstLaunch

    val fontSize: StateFlow<FontSize> = fontSizeUseCase.fontSize.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        FontSize.DEFAULT
    )

    private val _selectedTheme: MutableStateFlow<ThemeType> =
        MutableStateFlow(ThemeType.SYSTEM)
    val selectedTheme: StateFlow<ThemeType> = _selectedTheme.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            launch { getFirstLaunch() }
            launch { getThemeSettings() }
        }
    }

    private fun getFirstLaunch() {
        firstLaunchUseCase.isFirstLaunch.onEach { value ->
            _isFirstLaunch.value = value
        }.launchIn(viewModelScope)
    }

    private fun getThemeSettings() {
        themeSettingsUseCase.theme.onEach { value ->
            _selectedTheme.value = value
        }.launchIn(viewModelScope)
    }

    fun onFirstLaunch(screenType: ScreenType) {
        viewModelScope.launch {
            val initialFontSize = when (screenType) {
                ScreenType.SMALL -> FontSize.SMALL
                ScreenType.MEDIUM -> FontSize.DEFAULT
                ScreenType.LARGE -> FontSize.LARGE
                ScreenType.EXTRA_LARGE -> FontSize.EXTRA_LARGE
            }
            fontSizeUseCase.updateFontSize(initialFontSize)
        }
    }
}