package com.vassev.chatapp.data.remote

import com.vassev.chatapp.data.remote.dto.MessageDTO
import com.vassev.chatapp.domain.model.Message
import io.ktor.client.*
import io.ktor.client.request.*

class MessageServiceImpl(
    private val client: HttpClient
): MessageService {

    override suspend fun getAllMessages(): List<Message> {
        return try {
            client.get<List<MessageDTO>>(MessageService.Endpoints.GetAllMessages.url)
                .map { it.fromDTOtoEntity() }
        } catch (e: Exception) {
            emptyList()
        }
    }
}