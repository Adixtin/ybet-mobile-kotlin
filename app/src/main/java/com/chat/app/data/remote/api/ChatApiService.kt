package com.chat.app.data.remote.api

import com.google.gson.annotations.SerializedName
import retrofit2.Response
import retrofit2.http.*

// Request bodies

data class LoginRequest(val username: String)

data class SendMessageRequest(val content: String)

data class EditMessageRequest(
    @SerializedName("message_id") val messageId: String,
    val content: String
)

data class DeleteMessageRequest(
    @SerializedName("message_id") val messageId: String
)

// Response bodies

data class LoginResponse(val token: String)

data class MessageDto(
    @SerializedName("message_id") val messageId: String,
    @SerializedName("user_id") val userId: String,
    val username: String,
    val content: String,
    val timestamp: Long
)

data class MessagesResponse(
    val success: Boolean,
    val messages: List<MessageDto>? = null,
    val error: String? = null
)

data class SendMessageResponse(
    @SerializedName("message_id") val messageId: String,
    val timestamp: Long
)

// Retrofit interface

interface ChatApiService {

    @POST("login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @GET("messages")
    suspend fun getMessages(
        @Query("limit") limit: Int = 50
    ): Response<MessagesResponse>

    @POST("messages")
    suspend fun sendMessage(
        @Body request: SendMessageRequest
    ): Response<SendMessageResponse>

    @PATCH("messages")
    suspend fun editMessage(
        @Body request: EditMessageRequest
    ): Response<Unit>

    @HTTP(method = "DELETE", path = "messages", hasBody = true)
    suspend fun deleteMessage(
        @Body request: DeleteMessageRequest
    ): Response<Unit>
}