package com.vassev.chatapp.data.remote

import com.vassev.chatapp.domain.model.Message

interface MessageService {

    suspend fun getAllMessages(): List<Message>

    companion object {
        // local IP of my PC
        private const val localIP = "192.168.0.15"
        // IP of the android emulator
        private const val emulatorIP = "10.0.2.2"

        const val BASE_URL = "http://$localIP:8080"
    }

    sealed class Endpoints(val url: String) {
        object GetAllMessages: Endpoints("$BASE_URL/messages")
    }
}