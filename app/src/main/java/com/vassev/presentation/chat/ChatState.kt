package com.vassev.presentation.chat

import com.vassev.chatapp.domain.model.Message

data class ChatState(
    val messages: List<Message> = emptyList(),
    val isLoading: Boolean = false
)
