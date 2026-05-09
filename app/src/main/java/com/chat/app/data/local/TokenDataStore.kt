package com.chat.app.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "chat_prefs")

@Singleton
class TokenDataStore @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val AUTH_TOKEN_KEY     = stringPreferencesKey("auth_token")
    private val REFRESH_TOKEN_KEY  = stringPreferencesKey("refresh_token")
    private val USERNAME_KEY       = stringPreferencesKey("username")
    private val USER_ID_KEY        = stringPreferencesKey("user_id")

    suspend fun saveAuthToken(token: String) {
        context.dataStore.edit { it[AUTH_TOKEN_KEY] = token }
    }

    suspend fun getAuthToken(): String? =
        context.dataStore.data.map { it[AUTH_TOKEN_KEY] }.first()

    suspend fun saveRefreshToken(token: String) {
        context.dataStore.edit { it[REFRESH_TOKEN_KEY] = token }
    }

    suspend fun getRefreshToken(): String? =
        context.dataStore.data.map { it[REFRESH_TOKEN_KEY] }.first()

    suspend fun saveUsername(username: String) {
        context.dataStore.edit { it[USERNAME_KEY] = username }
    }

    suspend fun getUsername(): String? =
        context.dataStore.data.map { it[USERNAME_KEY] }.first()

    suspend fun saveUserId(userId: String) {
        context.dataStore.edit { it[USER_ID_KEY] = userId }
    }

    suspend fun getUserId(): String? =
        context.dataStore.data.map { it[USER_ID_KEY] }.first()

    suspend fun clear() {
        context.dataStore.edit { it.clear() }
    }
}
