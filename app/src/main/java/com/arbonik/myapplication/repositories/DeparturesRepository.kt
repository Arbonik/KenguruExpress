package com.arbonik.myapplication.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.arbonik.myapplication.database.KenguruDatabase
import com.arbonik.myapplication.network.Common
import com.arbonik.myapplication.network.models.DeparturesRequest
import com.arbonik.myapplication.network.models.DeparturesResponse
import com.arbonik.myapplication.network.models.tariff.TariffResponse
import com.arbonik.myapplication.network.references.SERVER_IP
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import okhttp3.WebSocketListener

class DeparturesRepository(database : KenguruDatabase) {

    // создание отправления
    fun createDeparture(departuresRequest: DeparturesRequest) : DeparturesResponse? =
        Common.DEPARTURES.departuresCreate(departuresRequest).execute().body()

    //Для загрузки данных в базу
    val tariffDao = database.tariffDao()

    //Загрузка в базу данных
    fun downloadTariffsToDatabase(departuresResponse: DeparturesResponse) : LiveData<TariffResponse>{
        val result: MutableLiveData<TariffResponse> = MutableLiveData()

        val id = departuresResponse.id

        val defaultLinkWebSocket = "ws://$SERVER_IP/ws/calculation/$id/"
        val client = OkHttpClient()
        val request = Request.Builder()
            .url(defaultLinkWebSocket)
            .build()
        client.newWebSocket(request, object : WebSocketListener() {
            val gsonConverter = GsonBuilder().create()
            override fun onMessage(webSocket: WebSocket, text: String) {
                if (text.contains("completed"))
                else {
                    val res = gsonConverter.fromJson(text, TariffResponse::class.java)
                    result.postValue(res)
                    tariffDao.insert(res.data!!)
                }
            }
        })
        return result
    }

}