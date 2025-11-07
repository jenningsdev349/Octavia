package com.jenningsdev.octavia.data.repositories

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.client.plugins.websocket.webSocket
import io.ktor.serialization.kotlinx.json.json
import io.ktor.websocket.Frame
import io.ktor.websocket.readText

class WebSocketClient {
    val client = HttpClient {
        install(WebSockets)
        install(ContentNegotiation) {
            json()
        }
    }

    suspend fun connectToStream() {
        try{
            client.webSocket("ws://10.0.2.2:8080/mockData") {
                Log.d("BANANA", "Connected to stream")
                for (frame in incoming) {
                    if (frame is Frame.Text) {
                        val data = frame.readText()
                        Log.d("BANANA", "Received: $data")
                    }
                }
            }
        } catch (e: Exception){
            Log.e("BANANA", "WebSocket handshake failed: ${e.message}", e)
        }
    }
}