package com.chat.app.presentation.serverconfig

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chat.app.data.local.ServerConfigDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ServerConfigUiState(
    val httpHost: String = ServerConfigDataStore.DEFAULT_HTTP_HOST,
    val wsHost: String   = ServerConfigDataStore.DEFAULT_WS_HOST,
    val isSaving: Boolean = false
)

@HiltViewModel
class ServerConfigViewModel @Inject constructor(
    private val serverConfig: ServerConfigDataStore
) : ViewModel() {

    private val _uiState = MutableStateFlow(ServerConfigUiState())
    val uiState: StateFlow<ServerConfigUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    httpHost = serverConfig.getHttpHost(),
                    wsHost   = serverConfig.getWsHost()
                )
            }
        }
    }

    fun onHttpHostChange(value: String) = _uiState.update { it.copy(httpHost = value) }
    fun onWsHostChange(value: String)   = _uiState.update { it.copy(wsHost = value) }

    fun confirm(onDone: () -> Unit) {
        viewModelScope.launch {
            _uiState.update { it.copy(isSaving = true) }
            val http = _uiState.value.httpHost.trim().ifBlank { ServerConfigDataStore.DEFAULT_HTTP_HOST }
            val ws   = _uiState.value.wsHost.trim().ifBlank { ServerConfigDataStore.DEFAULT_WS_HOST }
            serverConfig.saveHttpHost(http)
            serverConfig.saveWsHost(ws)
            _uiState.update { it.copy(isSaving = false) }
            onDone()
        }
    }

    fun resetDefaults() {
        _uiState.update {
            it.copy(
                httpHost = ServerConfigDataStore.DEFAULT_HTTP_HOST,
                wsHost   = ServerConfigDataStore.DEFAULT_WS_HOST
            )
        }
    }
}
