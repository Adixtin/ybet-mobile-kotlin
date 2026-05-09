package com.chat.app.data.remote.api

import com.google.gson.annotations.SerializedName
import retrofit2.Response
import retrofit2.http.*

// Request bodies

data class SigninRequest(
    val username: String,
    val password: String
)

data class LoginRequest(
    val username: String,
    val password: String
)

data class RefreshTokenRequest(
    @SerializedName("refresh_token") val refreshToken: String
)

data class SendMessageRequest(val content: String)

data class EditMessageRequest(val content: String)

// Response bodies

data class AuthResponse(
    @SerializedName("refresh_token") val refreshToken: String,
    @SerializedName("auth_token") val authToken: String,
    val id: String
)

data class RefreshTokenResponse(
    @SerializedName("auth_token") val authToken: String
)

data class ErrorResponse(val error: String)

data class MessageDto(
    @SerializedName("message_id") val messageId: String,
    @SerializedName("user_id") val userId: String,
    val username: String,
    val content: String,
    val timestamp: Long
)

data class MessagesResponse(
    val messages: List<MessageDto>
)

data class SendMessageResponse(
    @SerializedName("message_id") val messageId: String,
    val timestamp: Long
)

// Retrofit interface

interface ChatApiService {

    @POST("user/signin")
    suspend fun signin(@Body request: SigninRequest): Response<AuthResponse>

    @POST("user/login")
    suspend fun login(@Body request: LoginRequest): Response<AuthResponse>

    @POST("user/auth-token")
    suspend fun refreshAuthToken(@Body request: RefreshTokenRequest): Response<RefreshTokenResponse>

    @GET("messages")
    suspend fun getMessages(
        @Query("limit") limit: Int = 50
    ): Response<MessagesResponse>

    @POST("messages")
    suspend fun sendMessage(
        @Body request: SendMessageRequest
    ): Response<SendMessageResponse>

    @PATCH("messages/{messageId}")
    suspend fun editMessage(
        @Path("messageId") messageId: String,
        @Body request: EditMessageRequest
    ): Response<Unit>

    @DELETE("messages/{messageId}")
    suspend fun deleteMessage(
        @Path("messageId") messageId: String
    ): Response<Unit>
}