package com.chat.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import com.chat.app.data.local.ThemeDataStore
import com.chat.app.presentation.navigation.ChatNavGraph
import com.chat.app.ui.theme.ChatAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var themeDataStore: ThemeDataStore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val isDark by themeDataStore.isDarkTheme.collectAsState(initial = true)
            val fontSizeMultiplier by themeDataStore.fontSizeMultiplier.collectAsState(initial = 1.0f)
            val accentColorInt by themeDataStore.accentColor.collectAsState(initial = 0xFF2D5A8A.toInt())
            val isRoundedBubbles by themeDataStore.isRoundedBubbles.collectAsState(initial = true)
            
            val scope = rememberCoroutineScope()
            val accentColor = Color(accentColorInt)

            ChatAppTheme(
                darkTheme = isDark,
                fontSizeMultiplier = fontSizeMultiplier,
                accentColor = accentColor
            ) {
                ChatNavGraph(
                    isDarkTheme = isDark,
                    onToggleTheme = {
                        scope.launch { themeDataStore.setDarkTheme(!isDark) }
                    },
                    fontSizeMultiplier = fontSizeMultiplier,
                    onFontSizeChange = { multiplier ->
                        scope.launch { themeDataStore.setFontSizeMultiplier(multiplier) }
                    },
                    accentColor = accentColor,
                    onAccentColorChange = { color ->
                        scope.launch { themeDataStore.setAccentColor(color.toArgb()) }
                    },
                    isRoundedBubbles = isRoundedBubbles,
                    onRoundedBubblesChange = { rounded ->
                        scope.launch { themeDataStore.setIsRoundedBubbles(rounded) }
                    }
                )
            }
        }
    }

    private fun Color.toArgb(): Int {
        return (this.alpha * 255.0f + 0.5f).toInt() shl 24 or
               (this.red * 255.0f + 0.5f).toInt() shl 16 or
               (this.green * 255.0f + 0.5f).toInt() shl 8 or
               (this.blue * 255.0f + 0.5f).toInt()
    }
}
