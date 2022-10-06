package com.vassev.chatapp.data.remote

import com.vassev.chatapp.domain.model.Message
import com.vassev.chatapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface ChatSocketService {

    suspend fun startSession(
        username: String
    ): Resource<Unit>

    suspend fun sendMessage(message: String)

    fun observeMessages(): Flow<Message>

    suspend fun closeSession()

    companion object {
        // local IP of my PC
        private const val localIP = "192.168.0.15"
        // IP of the android emulator
        private const val emulatorIP = "10.0.2.2"

        const val BASE_URL = "ws://$localIP:8080"
    }

    sealed class Endpoints(val url: String) {
        object ChatSocket: Endpoints("$BASE_URL/chat-socket")
    }
}