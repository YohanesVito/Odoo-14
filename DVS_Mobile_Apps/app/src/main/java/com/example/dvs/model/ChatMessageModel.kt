package com.example.dvs.model

data class ChatMessageModel(
    val receiver: String,
    val title: String,
    val body: String
)

data class ChatModel(
    val chatId: String,
    val messages: List<MessageModel>
)

data class MessageModel(
    val messageId: String,
    val senderId: String,
    val recipientId: String,
    val timestamp: Long,
    val content: String
)

