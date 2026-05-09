package com.chat.app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
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

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF2D5A8A),
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFFD4E4F7),
    onPrimaryContainer = Color(0xFF0D2F50),
    secondary = Color(0xFF4A7AA8),
    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF1A1C1E),
    background = Color(0xFFF2F5F8),
    onBackground = Color(0xFF1A1C1E),
    surfaceVariant = Color(0xFFE8EEF5),
    onSurfaceVariant = Color(0xFF3C4A57),
    outline = Color(0xFFB0BEC5),
    error = Color(0xFFB3261E),
)

@Composable
fun ChatAppTheme(
    darkTheme: Boolean = true,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme,
        content = content
    )
}
