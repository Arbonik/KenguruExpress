package com.arbonik.myapplication.network.references

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val SERVER_IP = "94.230.114.34"
object RetrofitClient {
    val BASE_URL = "http://$SERVER_IP/"

    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}

