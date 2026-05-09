package com.chat.app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp

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
    fontSizeMultiplier: Float = 1.0f,
    content: @Composable () -> Unit
) {
    val typography = Typography()
    val scaledTypography = Typography(
        displayLarge = typography.displayLarge.copy(fontSize = typography.displayLarge.fontSize * fontSizeMultiplier),
        displayMedium = typography.displayMedium.copy(fontSize = typography.displayMedium.fontSize * fontSizeMultiplier),
        displaySmall = typography.displaySmall.copy(fontSize = typography.displaySmall.fontSize * fontSizeMultiplier),
        headlineLarge = typography.headlineLarge.copy(fontSize = typography.headlineLarge.fontSize * fontSizeMultiplier),
        headlineMedium = typography.headlineMedium.copy(fontSize = typography.headlineMedium.fontSize * fontSizeMultiplier),
        headlineSmall = typography.headlineSmall.copy(fontSize = typography.headlineSmall.fontSize * fontSizeMultiplier),
        titleLarge = typography.titleLarge.copy(fontSize = typography.titleLarge.fontSize * fontSizeMultiplier),
        titleMedium = typography.titleMedium.copy(fontSize = typography.titleMedium.fontSize * fontSizeMultiplier),
        titleSmall = typography.titleSmall.copy(fontSize = typography.titleSmall.fontSize * fontSizeMultiplier),
        bodyLarge = typography.bodyLarge.copy(fontSize = typography.bodyLarge.fontSize * fontSizeMultiplier),
        bodyMedium = typography.bodyMedium.copy(fontSize = typography.bodyMedium.fontSize * fontSizeMultiplier),
        bodySmall = typography.bodySmall.copy(fontSize = typography.bodySmall.fontSize * fontSizeMultiplier),
        labelLarge = typography.labelLarge.copy(fontSize = typography.labelLarge.fontSize * fontSizeMultiplier),
        labelMedium = typography.labelMedium.copy(fontSize = typography.labelMedium.fontSize * fontSizeMultiplier),
        labelSmall = typography.labelSmall.copy(fontSize = typography.labelSmall.fontSize * fontSizeMultiplier)
    )

    MaterialTheme(
        colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme,
        typography = scaledTypography,
        content = content
    )
}
