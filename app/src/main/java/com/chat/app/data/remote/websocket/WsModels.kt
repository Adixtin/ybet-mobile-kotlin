package com.chat.app.data.remote.websocket

import com.google.gson.annotations.SerializedName

data class WsEnvelope(
    val type: String,
    val payload: Map<String, Any?>
)

// Incoming parsed payloads
data class WsUserMessage(
    @SerializedName("message_id") val messageId: String,
    @SerializedName("user_id") val userId: String,
    val username: String,
    val content: String,
    val timestamp: Long
)

data class WsEditMessage(
    @SerializedName("message_id") val messageId: String,
    val content: String
)

data class WsDeleteMessage(
    @SerializedName("message_id") val messageId: String
)

data class WsSystemMessage(
    val content: String
)

data class WsUserListUpdate(
    val action: String, // "connect" | "disconnect"
    @SerializedName("user_id") val userId: String,
    val username: String
)

sealed class WsEvent {
    data class NewMessage(val message: WsUserMessage) : WsEvent()
    data class EditedMessage(val edit: WsEditMessage) : WsEvent()
    data class DeletedMessage(val delete: WsDeleteMessage) : WsEvent()
    data class SystemMessage(val message: WsSystemMessage) : WsEvent()
    data class UserListUpdate(val update: WsUserListUpdate) : WsEvent()
    data object Connected : WsEvent()
    data object Disconnected : WsEvent()
    data class Error(val throwable: Throwable) : WsEvent()
}
