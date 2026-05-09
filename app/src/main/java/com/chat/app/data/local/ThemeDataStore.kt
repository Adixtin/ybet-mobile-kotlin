package com.chat.app.data.local

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
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

    /** Emits the current dark-theme preference (default: true). */
    val isDarkTheme: Flow<Boolean> =
        context.dataStore.data.map { it[DARK_THEME_KEY] ?: true }

    /** Emits the current font size multiplier (default: 1.0f). */
    val fontSizeMultiplier: Flow<Float> =
        context.dataStore.data.map { it[FONT_SIZE_MULTIPLIER_KEY] ?: 1.0f }

    suspend fun setDarkTheme(dark: Boolean) {
        context.dataStore.edit { it[DARK_THEME_KEY] = dark }
    }

    suspend fun setFontSizeMultiplier(multiplier: Float) {
        context.dataStore.edit { it[FONT_SIZE_MULTIPLIER_KEY] = multiplier }
    }
}
