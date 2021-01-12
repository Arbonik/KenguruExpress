package com.arbonik.myapplication.product

import android.util.Log
import com.arbonik.myapplication.network.Common
import com.arbonik.myapplication.network.data.DeparturesRequest
import com.arbonik.myapplication.network.data.DeparturesResponce
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.SocketAddress
import javax.net.SocketFactory

class Calculator {
    var fromIdSity : Int = 1
    var toIdSity : Int? = 2

    fun calculate(/*mutableLiveData : MutableLiveData<DeparturesResponce>*/){
        val req = DeparturesRequest(
            listOf(408), true, true, toIdSity, fromIdSity, null
        )
        Common.DEPARTURES.departuresCreate(req).enqueue(object : Callback<DeparturesResponce> {
            override fun onResponse(
                call: Call<DeparturesResponce>,
                response: Response<DeparturesResponce>
            ) {
                var id = response.body()?.id
                Log.d("Calculator", response.body().toString())
                var defaultLinkWebSocket = "ws://68.183.30.45/ws/calculation/$id/"
                Log.d("Calculator", id.toString())

                val client = OkHttpClient()
                Log.d("Calculator", "client")

                val request = Request.Builder()
                    .url(defaultLinkWebSocket)
                    .build()
                Log.d("Calculator", "request")

                val socket = client.newWebSocket(request, object : WebSocketListener (){
                    override fun onOpen(webSocket: WebSocket, response: okhttp3.Response) {
                        Log.d("Calculator", response.body().toString())

                    }

                    override fun onMessage(webSocket: WebSocket, text: String) {
                        Log.d("Calculator", text.toString())
                    }

                    override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
                        Log.d("Calculator", bytes.toString())
                    }

                    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
                        Log.d("Calculator", reason.toString())
                    }

                    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                        Log.d("Calculator", reason.toString())
                    }

                    override fun onFailure(
                        webSocket: WebSocket,
                        t: Throwable,
                        response: okhttp3.Response?
                    ) {
                        Log.d("Calculator", t.toString())
                        Log.d("Calculator", response.toString())
                        Log.d("Calculator", webSocket.toString())
                        super.onFailure(webSocket, t, response)
                    }
                })

            }

            override fun onFailure(call: Call<DeparturesResponce>, t: Throwable) {
                Log.d("Calculator", t.message.toString())
            }
        }
        )
    }
}