package com.chat.app.data.repository

import android.util.Base64
import com.chat.app.data.local.TokenDataStore
import com.chat.app.data.remote.api.ChatApiService
import com.chat.app.data.remote.api.DeleteMessageRequest
import com.chat.app.data.remote.api.EditMessageRequest
import com.chat.app.data.remote.api.LoginRequest
import com.chat.app.data.remote.api.SendMessageRequest
import com.chat.app.data.remote.websocket.WsEvent
import com.chat.app.data.remote.websocket.WebSocketManager
import com.chat.app.domain.model.Message
import kotlinx.coroutines.flow.Flow
import org.json.JSONObject
import javax.inject.Inject
import javax.inject.Singleton

sealed class Result<out T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Error(val message: String) : Result<Nothing>()
}

@Singleton
class ChatRepository @Inject constructor(
    private val apiService: ChatApiService,
    private val webSocketManager: WebSocketManager,
    private val tokenDataStore: TokenDataStore
) {

    suspend fun login(username: String): Result<Unit> = try {
        val response = apiService.login(LoginRequest(username))
        if (response.isSuccessful && response.body() != null) {
            val token = response.body()!!.token
            tokenDataStore.saveToken(token)
            tokenDataStore.saveUsername(username)

            try {
                val payload = token.split(".")[1]
                val decoded = String(Base64.decode(payload, Base64.URL_SAFE or Base64.NO_PADDING))
                val json = JSONObject(decoded)
                val userId = json.optString("user_id", json.optString("sub", ""))
                if (userId.isNotBlank()) tokenDataStore.saveUserId(userId)
            } catch (_: Exception) {}

            Result.Success(Unit)
        } else {
            Result.Error("Login failed: ${response.code()}")
        }
    } catch (e: Exception) {
        Result.Error(e.message ?: "Unknown error")
    }

    suspend fun logout() {
        webSocketManager.disconnect()
        tokenDataStore.clear()
    }

    suspend fun isLoggedIn(): Boolean = tokenDataStore.getToken() != null

    suspend fun getCurrentUserId(): String? = tokenDataStore.getUserId()
    suspend fun getCurrentUsername(): String? = tokenDataStore.getUsername()

    suspend fun getMessages(limit: Int = 50): Result<List<Message>> = try {
        val response = apiService.getMessages(limit)
        if (response.isSuccessful && response.body()?.success == true) {
            val messages = response.body()!!.messages.orEmpty().map {
                Message(it.messageId, it.userId, it.username, it.content, it.timestamp)
            }
            Result.Success(messages)
        } else {
            Result.Error(response.body()?.error ?: "Failed to load messages")
        }
    } catch (e: Exception) {
        Result.Error(e.message ?: "Unknown error")
    }

    suspend fun sendMessage(content: String): Result<Unit> = try {
        val response = apiService.sendMessage(SendMessageRequest(content))
        if (response.isSuccessful) Result.Success(Unit)
        else Result.Error("Send failed: ${response.code()}")
    } catch (e: Exception) {
        Result.Error(e.message ?: "Unknown error")
    }

    suspend fun editMessage(messageId: String, content: String): Result<Unit> = try {
        val response = apiService.editMessage(EditMessageRequest(messageId, content))
        when (response.code()) {
            200 -> Result.Success(Unit)
            403 -> Result.Error("You can't edit this message")
            400 -> Result.Error("Bad request")
            else -> Result.Error("Error: ${response.code()}")
        }
    } catch (e: Exception) {
        Result.Error(e.message ?: "Unknown error")
    }

    suspend fun deleteMessage(messageId: String): Result<Unit> = try {
        val response = apiService.deleteMessage(DeleteMessageRequest(messageId))
        when (response.code()) {
            200 -> Result.Success(Unit)
            403 -> Result.Error("You can't delete this message")
            400 -> Result.Error("Bad request")
            else -> Result.Error("Error: ${response.code()}")
        }
    } catch (e: Exception) {
        Result.Error(e.message ?: "Unknown error")
    }

    suspend fun observeWebSocket(): Flow<WsEvent>? {
        val token = tokenDataStore.getToken() ?: return null
        return webSocketManager.observe(token)
    }
}
