package com.arbonik.myapplication.network.websocket

import android.util.Log
import okhttp3.*
import okio.ByteString

class WebSocketDepartures(val id : String) {
    val client =  OkHttpClient()
    val request = Request.Builder()
            .url("ws://68.183.30.45/ws/calculation/$id")
        .build()
    init {
        client.newWebSocket(request, object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                super.onOpen(webSocket, response)
                Log.d("WEBSOCKET","super.onOpen(webSocket, response)")
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                super.onMessage(webSocket, text)
                Log.d("WEBSOCKET","super.onMessage(webSocket, text)")
            }

            override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
                super.onMessage(webSocket, bytes)
                Log.d("WEBSOCKET","super.onMessage(webSocket, bytes)")
            }

            override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
                super.onClosing(webSocket, code, reason)
                Log.d("WEBSOCKET","super.onClosing(webSocket, code, reason)")
            }

            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                super.onClosed(webSocket, code, reason)
                Log.d("WEBSOCKET","super.onClosed(webSocket, code, reason)")
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                super.onFailure(webSocket, t, response)
                Log.d("WEBSOCKET", t.message.toString())
            }
        })
    }
}