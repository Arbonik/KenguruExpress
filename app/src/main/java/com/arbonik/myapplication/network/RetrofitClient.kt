package com.arbonik.myapplication.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection

object RetrofitClient {
    val BASE_URL = "http://68.183.30.45/"

    var retrofit : Retrofit? = null
            get() {
                if (field == null)
                    field = Retrofit.Builder()
                            .baseUrl(BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build()
                return field
            }
}

