package com.example.valorant

import android.os.Bundle
import android.util.DisplayMetrics
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.os.LocaleListCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.valorant.core.common.util.network.NetworkMonitor
import com.example.valorant.core.uikit.theme.ValorantTheme
import com.example.valorant.core.uikit.util.LocalScreenInfo
import com.example.valorant.domain.model.common.device.FontSize
import com.example.valorant.domain.model.common.device.ScreenInfo
import com.example.valorant.domain.model.common.device.ScreenType
import com.example.valorant.domain.model.user.LanguageType
import com.example.valorant.ui.ValorantApp
import com.example.valorant.ui.rememberValorantAppState
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel by viewModels<MainViewModel>()

    @Inject
    lateinit var networkMonitor: NetworkMonitor

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        var keepSplashScreen by mutableStateOf(true)
        enableEdgeToEdge()
        AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags("en"))
        splashScreen.setKeepOnScreenCondition {
            keepSplashScreen
        }

        setContent {
            val isFirstLaunch by mainViewModel.isFirstLaunch.collectAsState()
            val fontSizePrefs by mainViewModel.fontSize.collectAsState()
            val themeType by mainViewModel.selectedTheme.collectAsState()
            val language by mainViewModel.selectedLanguage.collectAsState()

            val screenInfo = remember { getScreenInfo() }

            LaunchedEffect(isFirstLaunch) {
                if (isFirstLaunch != null) {
                    if (isFirstLaunch as Boolean) {
                        mainViewModel.onFirstLaunch(screenInfo.screenType)
                    }
                    keepSplashScreen = false
                }
            }

            LaunchedEffect(language) {
                if (language != LanguageType.SYSTEM) {
                    AppCompatDelegate.setApplicationLocales(
                        LocaleListCompat.forLanguageTags(language.code.split("-")[0])
                    )
                } else {
                    AppCompatDelegate.setApplicationLocales(LocaleListCompat.getEmptyLocaleList())
                }
            }

            if (isFirstLaunch != null) {
                val updatedScreenInfo = screenInfo.copy(fontSizePrefs = fontSizePrefs)
                val appState = rememberValorantAppState(
                    networkMonitor = networkMonitor,
                )
                CompositionLocalProvider(LocalScreenInfo provides updatedScreenInfo) {
                    ValorantTheme(themeType) {
                        ValorantApp(appState)
                    }
                }
            }
        }
    }

    private fun getScreenInfo(): ScreenInfo {
        val displayMetrics: DisplayMetrics = resources.displayMetrics
        val density: Float = displayMetrics.density

        val widthDp: Float = displayMetrics.widthPixels / density
        val heightDp: Float = displayMetrics.heightPixels / density

        val (portraitWidthDp: Float, portraitHeightDp: Float) = if (widthDp < heightDp) {
            widthDp to heightDp
        } else {
            heightDp to widthDp
        }

        val screenType: ScreenType = when {
            portraitWidthDp < 360 -> ScreenType.SMALL
            portraitWidthDp in 360.0..480.0 -> ScreenType.MEDIUM
            portraitWidthDp in 480.0..600.0 -> ScreenType.LARGE
            else -> ScreenType.EXTRA_LARGE
        }

        return ScreenInfo(screenType, FontSize.DEFAULT, portraitWidthDp.toInt(), portraitHeightDp.toInt())
    }
}
