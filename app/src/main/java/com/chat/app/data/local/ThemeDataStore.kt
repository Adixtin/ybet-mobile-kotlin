package com.chat.app.data.local

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ThemeDataStore @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val DARK_THEME_KEY = booleanPreferencesKey("dark_theme")
    private val FONT_SIZE_MULTIPLIER_KEY = floatPreferencesKey("font_size_multiplier")
    private val ACCENT_COLOR_KEY = intPreferencesKey("accent_color")
    private val IS_ROUNDED_BUBBLES_KEY = booleanPreferencesKey("is_rounded_bubbles")

    /** Emits the current dark-theme preference (default: true). */
    val isDarkTheme: Flow<Boolean> =
        context.dataStore.data.map { it[DARK_THEME_KEY] ?: true }

    /** Emits the current font size multiplier (default: 1.0f). */
    val fontSizeMultiplier: Flow<Float> =
        context.dataStore.data.map { it[FONT_SIZE_MULTIPLIER_KEY] ?: 1.0f }

    /** Emits the current accent color as ARGB (default: Blue-ish 0xFF2D5A8A). */
    val accentColor: Flow<Int> =
        context.dataStore.data.map { it[ACCENT_COLOR_KEY] ?: 0xFF2D5A8A.toInt() }

    /** Emits whether bubbles should be rounded (default: true). */
    val isRoundedBubbles: Flow<Boolean> =
        context.dataStore.data.map { it[IS_ROUNDED_BUBBLES_KEY] ?: true }

    suspend fun setDarkTheme(dark: Boolean) {
        context.dataStore.edit { it[DARK_THEME_KEY] = dark }
    }

    suspend fun setFontSizeMultiplier(multiplier: Float) {
        context.dataStore.edit { it[FONT_SIZE_MULTIPLIER_KEY] = multiplier }
    }

    suspend fun setAccentColor(colorArgb: Int) {
        context.dataStore.edit { it[ACCENT_COLOR_KEY] = colorArgb }
    }

    suspend fun setIsRoundedBubbles(rounded: Boolean) {
        context.dataStore.edit { it[IS_ROUNDED_BUBBLES_KEY] = rounded }
    }
}
