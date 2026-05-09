package com.chat.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
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
            val scope = rememberCoroutineScope()

            ChatAppTheme(
                darkTheme = isDark,
                fontSizeMultiplier = fontSizeMultiplier
            ) {
                ChatNavGraph(
                    isDarkTheme = isDark,
                    onToggleTheme = {
                        scope.launch { themeDataStore.setDarkTheme(!isDark) }
                    },
                    fontSizeMultiplier = fontSizeMultiplier,
                    onFontSizeChange = { multiplier ->
                        scope.launch { themeDataStore.setFontSizeMultiplier(multiplier) }
                    }
                )
            }
        }
    }
}
