package com.example.dvs.model

import android.os.IBinder.DeathRecipient

data class ChatMessageModel(
    val receiver: String,
    val title: String,
    val body: String
)

data class ChatModel(
    val chatId: String,
    val senderId: String,
    val recipientId: String,
    val content: String
)