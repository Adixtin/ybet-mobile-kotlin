package com.chat.app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF7C9CBF),
    onPrimary = Color(0xFF1A1A2E),
    primaryContainer = Color(0xFF2D4A6B),
    onPrimaryContainer = Color(0xFFCCDEF0),
    secondary = Color(0xFF9BB5CC),
    surface = Color(0xFF1A1A2E),
    onSurface = Color(0xFFE8EAF6),
    background = Color(0xFF12121F),
    onBackground = Color(0xFFE8EAF6),
    surfaceVariant = Color(0xFF252540),
    onSurfaceVariant = Color(0xFFBBBDD0),
    outline = Color(0xFF44476A),
    error = Color(0xFFCF6679),
)

@Composable
fun ChatAppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        content = content
    )
}
