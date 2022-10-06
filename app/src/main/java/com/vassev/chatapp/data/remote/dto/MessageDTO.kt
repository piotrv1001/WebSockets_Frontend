package com.vassev.chatapp.data.remote.dto

import com.vassev.chatapp.domain.model.Message
import kotlinx.serialization.Serializable
import java.text.DateFormat
import java.util.*

@Serializable
data class MessageDTO(
    val id: String,
    val text: String,
    val username: String,
    val timestamp: Long
) {
    fun fromDTOtoEntity(): Message {
        val date = Date(timestamp)
        val formattedDate = DateFormat
            .getDateInstance(DateFormat.DEFAULT)
            .format(date)

        return Message(
            text = text,
            username = username,
            formattedTime = formattedDate
        )
    }
}

