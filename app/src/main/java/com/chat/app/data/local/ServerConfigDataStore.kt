package com.chat.app.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ServerConfigDataStore @Inject constructor(
    @ApplicationContext private val context: Context
) {
    companion object {
        const val DEFAULT_HTTP_HOST = "10.0.2.2:8080"
        const val DEFAULT_WS_HOST   = "10.0.2.2:8081"
    }

    private val HTTP_HOST_KEY = stringPreferencesKey("server_http_host")
    private val WS_HOST_KEY   = stringPreferencesKey("server_ws_host")

    suspend fun saveHttpHost(host: String) {
        context.dataStore.edit { it[HTTP_HOST_KEY] = host }
    }

    suspend fun getHttpHost(): String =
        context.dataStore.data.map { it[HTTP_HOST_KEY] ?: DEFAULT_HTTP_HOST }.first()

    suspend fun saveWsHost(host: String) {
        context.dataStore.edit { it[WS_HOST_KEY] = host }
    }

    suspend fun getWsHost(): String =
        context.dataStore.data.map { it[WS_HOST_KEY] ?: DEFAULT_WS_HOST }.first()
}
