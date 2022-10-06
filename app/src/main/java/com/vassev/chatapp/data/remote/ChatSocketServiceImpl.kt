package com.vassev.chatapp.data.remote

import com.vassev.chatapp.data.remote.dto.MessageDTO
import com.vassev.chatapp.domain.model.Message
import com.vassev.chatapp.util.Resource
import io.ktor.client.*
import io.ktor.client.features.websocket.*
import io.ktor.client.request.*
import io.ktor.http.cio.websocket.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.isActive
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class ChatSocketServiceImpl(
    private val client: HttpClient
): ChatSocketService {

    private var socket: WebSocketSession? = null

    override suspend fun startSession(username: String): Resource<Unit> {
        return try {
            socket = client.webSocketSession {
                url("${ChatSocketService.Endpoints.ChatSocket.url}?username=$username")
            }
            if(socket?.isActive == true) {
                Resource.Success(Unit)
            } else Resource.Error("Could not establish connection")
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.localizedMessage ?: "Error")
        }
    }

    override suspend fun sendMessage(message: String) {
        try {
            socket?.send(Frame.Text(message))
        } catch(e: Exception) {
            e.printStackTrace()
        }
    }

    override fun observeMessages(): Flow<Message> {
        return try {
            socket?.incoming
                ?.receiveAsFlow()
                ?.filter { it is Frame.Text }
                ?.map {
                    val json = (it as? Frame.Text)?.readText() ?: ""
                    val messageDTO = Json.decodeFromString<MessageDTO>(json)
                    messageDTO.fromDTOtoEntity()
                } ?: flow { }
        } catch (e: Exception) {
            e.printStackTrace()
            flow { }
        }
    }

    override suspend fun closeSession() {
        socket?.close()
    }
}