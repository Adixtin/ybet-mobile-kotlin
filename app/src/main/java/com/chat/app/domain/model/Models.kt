package com.chat.app.domain.model

data class Message(
    val messageId: String,
    val userId: String,
    val username: String,
    val content: String,
    val timestamp: Long
)

data class OnlineUser(
    val userId: String,
    val username: String
)
