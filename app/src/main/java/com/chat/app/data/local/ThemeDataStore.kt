package com.chat.app.data.local

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
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

    /** Emits the current dark-theme preference (default: true). */
    val isDarkTheme: Flow<Boolean> =
        context.dataStore.data.map { it[DARK_THEME_KEY] ?: true }

    suspend fun setDarkTheme(dark: Boolean) {
        context.dataStore.edit { it[DARK_THEME_KEY] = dark }
    }
}
