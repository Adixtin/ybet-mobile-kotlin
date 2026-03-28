package com.chat.app.presentation.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chat.app.data.remote.websocket.WsEvent
import com.chat.app.data.repository.ChatRepository
import com.chat.app.data.repository.Result
import com.chat.app.domain.model.Message
import com.chat.app.domain.model.OnlineUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ChatUiState(
    val messages: List<Message> = emptyList(),
    val onlineUsers: List<OnlineUser> = emptyList(),
    val currentUserId: String? = null,
    val currentUsername: String? = null,
    val inputText: String = "",
    val isLoadingHistory: Boolean = false,
    val isSending: Boolean = false,
    val isConnected: Boolean = false,
    val snackbarMessage: String? = null,
    val editingMessageId: String? = null,
    val editInputText: String = "",
    val contextMenuMessageId: String? = null
)

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val repository: ChatRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ChatUiState())
    val uiState: StateFlow<ChatUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    currentUserId = repository.getCurrentUserId(),
                    currentUsername = repository.getCurrentUsername()
                )
            }
            loadHistory()
            connectWebSocket()
        }
    }


    fun loadHistory() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoadingHistory = true) }
            when (val result = repository.getMessages(50)) {
                is Result.Success -> _uiState.update {
                    it.copy(messages = result.data, isLoadingHistory = false)
                }
                is Result.Error -> _uiState.update {
                    it.copy(isLoadingHistory = false, snackbarMessage = result.message)
                }
            }
        }
    }


    private fun connectWebSocket() {
        viewModelScope.launch {
            val flow = repository.observeWebSocket() ?: return@launch
            flow.collect { event ->
                when (event) {
                    is WsEvent.Connected -> {
                        _uiState.update { it.copy(isConnected = true) }
                    }
                    is WsEvent.Disconnected -> {
                        _uiState.update { it.copy(isConnected = false) }
                    }
                    is WsEvent.Error -> {
                        _uiState.update {
                            it.copy(
                                isConnected = false,
                                snackbarMessage = "Connection lost, reconnecting…"
                            )
                        }
                    }
                    is WsEvent.NewMessage -> {
                        val msg = event.message
                        val newMsg = Message(msg.messageId, msg.userId, msg.username, msg.content, msg.timestamp)
                        _uiState.update { state ->
                            // Avoid duplicate if we already have it
                            if (state.messages.any { it.messageId == msg.messageId }) state
                            else state.copy(messages = state.messages + newMsg)
                        }
                    }
                    is WsEvent.EditedMessage -> {
                        val edit = event.edit
                        _uiState.update { state ->
                            state.copy(
                                messages = state.messages.map { msg ->
                                    if (msg.messageId == edit.messageId) msg.copy(content = edit.content)
                                    else msg
                                }
                            )
                        }
                    }
                    is WsEvent.DeletedMessage -> {
                        val del = event.delete
                        _uiState.update { state ->
                            state.copy(messages = state.messages.filter { it.messageId != del.messageId })
                        }
                    }
                    is WsEvent.SystemMessage -> {
                        _uiState.update { it.copy(snackbarMessage = "⚙ ${event.message.content}") }
                    }
                    is WsEvent.UserListUpdate -> {
                        val update = event.update
                        _uiState.update { state ->
                            val users = state.onlineUsers.toMutableList()
                            when (update.action) {
                                "connect" -> {
                                    if (users.none { it.userId == update.userId }) {
                                        users.add(OnlineUser(update.userId, update.username))
                                    }
                                }
                                "disconnect" -> users.removeAll { it.userId == update.userId }
                            }
                            state.copy(onlineUsers = users)
                        }
                    }
                }
            }
        }
    }


    fun onInputChange(text: String) = _uiState.update { it.copy(inputText = text) }

    fun sendMessage() {
        val content = _uiState.value.inputText.trim()
        if (content.isBlank()) return
        viewModelScope.launch {
            _uiState.update { it.copy(isSending = true, inputText = "") }
            when (val result = repository.sendMessage(content)) {
                is Result.Success -> _uiState.update { it.copy(isSending = false) }
                is Result.Error -> _uiState.update {
                    it.copy(isSending = false, snackbarMessage = result.message, inputText = content)
                }
            }
        }
    }


    fun startEdit(message: Message) {
        _uiState.update {
            it.copy(
                editingMessageId = message.messageId,
                editInputText = message.content,
                contextMenuMessageId = null
            )
        }
    }

    fun onEditInputChange(text: String) = _uiState.update { it.copy(editInputText = text) }

    fun confirmEdit() {
        val state = _uiState.value
        val id = state.editingMessageId ?: return
        val content = state.editInputText.trim()
        if (content.isBlank()) return
        viewModelScope.launch {
            when (val result = repository.editMessage(id, content)) {
                is Result.Success -> _uiState.update { it.copy(editingMessageId = null, editInputText = "") }
                is Result.Error -> _uiState.update {
                    it.copy(snackbarMessage = result.message, editingMessageId = null, editInputText = "")
                }
            }
        }
    }

    fun cancelEdit() = _uiState.update { it.copy(editingMessageId = null, editInputText = "") }


    fun deleteMessage(messageId: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(contextMenuMessageId = null) }
            when (val result = repository.deleteMessage(messageId)) {
                is Result.Success -> { /* WS event will remove it */ }
                is Result.Error -> _uiState.update { it.copy(snackbarMessage = result.message) }
            }
        }
    }


    fun showContextMenu(messageId: String) = _uiState.update { it.copy(contextMenuMessageId = messageId) }
    fun hideContextMenu() = _uiState.update { it.copy(contextMenuMessageId = null) }


    fun clearSnackbar() = _uiState.update { it.copy(snackbarMessage = null) }

    fun logout() {
        viewModelScope.launch { repository.logout() }
    }
}