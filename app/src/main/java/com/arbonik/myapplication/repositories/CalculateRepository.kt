package com.arbonik.myapplication.repositories

import androidx.lifecycle.MutableLiveData
import com.arbonik.myapplication.network.Common
import com.arbonik.myapplication.network.models.DeparturesRequest
import com.arbonik.myapplication.network.models.DeparturesResponce
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import okhttp3.WebSocketListener

class CalculateRepository {

    var fromCityId: Int = 1
    var toCityId: Int = 2

    val testReq = DeparturesRequest(
        listOf(408), true, true, toCityId, fromCityId, null
    )

    fun createDeparture(departuresRequest: DeparturesRequest = testReq) =
        Common.DEPARTURES.departuresCreate(departuresRequest).execute().body()

    val tariffs: MutableLiveData<String> = MutableLiveData()


    fun downloadTariffs(departuresResponce: DeparturesResponce? = createDeparture()) {
        val id = departuresResponce?.id
        val defaultLinkWebSocket = "ws://68.183.30.45/ws/calculation/$id/"
        val client = OkHttpClient()
        val request = Request.Builder()
            .url(defaultLinkWebSocket)
            .build()

        client.newWebSocket(request, object : WebSocketListener() {
            override fun onMessage(webSocket: WebSocket, text: String) {
                tariffs.postValue(text)
            }
        })
    }

}