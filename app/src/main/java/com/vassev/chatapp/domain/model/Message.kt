package com.vassev.chatapp.domain.model

import java.util.*

data class Message(
    val text: String,
    val formattedTime: String,
    val username: String
)