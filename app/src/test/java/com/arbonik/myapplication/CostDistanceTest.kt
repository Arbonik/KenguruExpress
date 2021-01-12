package com.arbonik.myapplication

import android.location.Location
import android.util.Log
import com.arbonik.myapplication.network.data.geography.instanceEmptyLocalityItem
import okhttp3.*
import okhttp3.internal.ws.WebSocketProtocol
import okio.ByteString
import org.junit.Test
import java.io.IOException
import java.net.SocketAddress

class CostDistanceTest {
    // координаты барнаула
    val client = OkHttpClient()
    val request = Request.Builder()
        .url("ws://68.183.30.45/ws/calculation/2")
        .build()

    @Test
    fun connectToWebSocket() {

//        client.socketFactory().createSocket("ws://68.183.30.45/ws/calculation/",80).connect()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {

                Log.d("WEBSOCKET", "super.onOpen(webSocket, response)")
            }

            override fun onResponse(call: Call, response: Response) {
                Log.d("WEBSOCKET", "super.onOpen(webSocket, response)")
            }
        })
        client.newWebSocket(request, object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                super.onOpen(webSocket, response)
                Log.d("WEBSOCKET", "super.onOpen(webSocket, response)")
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                super.onMessage(webSocket, text)
                Log.d("WEBSOCKET", "super.onMessage(webSocket, text)")
            }

            override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
                super.onMessage(webSocket, bytes)
                Log.d("WEBSOCKET", "super.onMessage(webSocket, bytes)")
            }

            override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
                super.onClosing(webSocket, code, reason)
                Log.d("WEBSOCKET", "super.onClosing(webSocket, code, reason)")
            }

            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                super.onClosed(webSocket, code, reason)
                Log.d("WEBSOCKET", "super.onClosed(webSocket, code, reason)")
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                super.onFailure(webSocket, t, response)
                Log.d("WEBSOCKET", t.message.toString())
            }
    })
    while (true){
        Thread.sleep(100)
    }
}
}