package com.chat.app.data.repository

import com.chat.app.data.local.TokenDataStore
import com.chat.app.data.remote.api.ChatApiService
import com.chat.app.data.remote.api.EditMessageRequest
import com.chat.app.data.remote.api.LoginRequest
import com.chat.app.data.remote.api.RefreshTokenRequest
import com.chat.app.data.remote.api.SendMessageRequest
import com.chat.app.data.remote.api.SigninRequest
import com.chat.app.data.remote.websocket.WsEvent
import com.chat.app.data.remote.websocket.WebSocketManager
import com.chat.app.domain.model.Message
import kotlinx.coroutines.flow.Flow
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

    /** Rejestracja nowego użytkownika */
    suspend fun signin(username: String, password: String): Result<Unit> = try {
        val response = apiService.signin(SigninRequest(username, password))
        if (response.isSuccessful && response.body() != null) {
            val body = response.body()!!
            tokenDataStore.saveAuthToken(body.authToken)
            tokenDataStore.saveRefreshToken(body.refreshToken)
            tokenDataStore.saveUserId(body.id)
            tokenDataStore.saveUsername(username)
            Result.Success(Unit)
        } else {
            Result.Error("Sign-in failed: ${response.code()}")
        }
    } catch (e: Exception) {
        Result.Error(e.message ?: "Unknown error")
    }

    /** Logowanie istniejącego użytkownika */
    suspend fun login(username: String, password: String): Result<Unit> = try {
        val response = apiService.login(LoginRequest(username, password))
        if (response.isSuccessful && response.body() != null) {
            val body = response.body()!!
            tokenDataStore.saveAuthToken(body.authToken)
            tokenDataStore.saveRefreshToken(body.refreshToken)
            tokenDataStore.saveUserId(body.id)
            tokenDataStore.saveUsername(username)
            Result.Success(Unit)
        } else {
            Result.Error("Login failed: ${response.code()}")
        }
    } catch (e: Exception) {
        Result.Error(e.message ?: "Unknown error")
    }

    /** Odświeżenie auth_token przy użyciu refresh_token */
    suspend fun refreshAuthToken(): Result<Unit> = try {
        val refreshToken = tokenDataStore.getRefreshToken()
            ?: return Result.Error("No refresh token available")
        val response = apiService.refreshAuthToken(RefreshTokenRequest(refreshToken))
        if (response.isSuccessful && response.body() != null) {
            tokenDataStore.saveAuthToken(response.body()!!.authToken)
            Result.Success(Unit)
        } else {
            Result.Error("Token refresh failed: ${response.code()}")
        }
    } catch (e: Exception) {
        Result.Error(e.message ?: "Unknown error")
    }

    suspend fun logout() {
        webSocketManager.disconnect()
        tokenDataStore.clear()
    }

    suspend fun isLoggedIn(): Boolean = tokenDataStore.getAuthToken() != null

    suspend fun getCurrentUserId(): String? = tokenDataStore.getUserId()
    suspend fun getCurrentUsername(): String? = tokenDataStore.getUsername()

    suspend fun getMessages(limit: Int = 50): Result<List<Message>> = try {
        val response = apiService.getMessages(limit)
        if (response.isSuccessful && response.body() != null) {
            val messages = response.body()!!.messages.map {
                Message(it.messageId, it.userId, it.username, it.content, it.timestamp)
            }
            Result.Success(messages)
        } else {
            Result.Error("Failed to load messages: ${response.code()}")
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
        val response = apiService.editMessage(messageId, EditMessageRequest(content))
        when (response.code()) {
            200  -> Result.Success(Unit)
            403  -> Result.Error("You can't edit this message")
            400  -> Result.Error("Bad request")
            401  -> Result.Error("Unauthorized")
            else -> Result.Error("Error: ${response.code()}")
        }
    } catch (e: Exception) {
        Result.Error(e.message ?: "Unknown error")
    }

    suspend fun deleteMessage(messageId: String): Result<Unit> = try {
        val response = apiService.deleteMessage(messageId)
        when (response.code()) {
            200  -> Result.Success(Unit)
            403  -> Result.Error("You can't delete this message")
            400  -> Result.Error("Bad request")
            401  -> Result.Error("Unauthorized")
            else -> Result.Error("Error: ${response.code()}")
        }
    } catch (e: Exception) {
        Result.Error(e.message ?: "Unknown error")
    }

    suspend fun observeWebSocket(): Flow<WsEvent>? {
        val token = tokenDataStore.getAuthToken() ?: return null
        return webSocketManager.observe(token)
    }
}
