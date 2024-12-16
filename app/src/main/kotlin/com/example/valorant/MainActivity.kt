package com.example.valorant

import android.os.Bundle
import android.util.DisplayMetrics
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import com.example.valorant.core.uikit.theme.ValorantTheme
import com.example.valorant.core.uikit.util.LocalScreenInfo
import com.example.valorant.domain.model.common.device.FontSize
import com.example.valorant.domain.model.common.device.ScreenInfo
import com.example.valorant.domain.model.common.device.ScreenType
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val updatedScreenInfo = remember { getScreenInfo() }
            CompositionLocalProvider(LocalScreenInfo provides updatedScreenInfo) {
                ValorantTheme {
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
            portraitWidthDp in 360.0..480.0 -> ScreenType.DEFAULT
            portraitWidthDp in 480.0..600.0 -> ScreenType.LARGE
            else -> ScreenType.EXTRA_LARGE
        }

        return ScreenInfo(screenType, FontSize.DEFAULT, portraitWidthDp.toInt(), portraitHeightDp.toInt())
    }
}
