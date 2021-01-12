package com.arbonik.myapplication.network

import com.arbonik.myapplication.network.data.services.ServicesItem
import com.arbonik.myapplication.network.data.services.ServicesRespone
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ServicesReference {
    @GET("departures/services/")
    fun getServices(): Call<ServicesRespone>

    @GET("departures/services/{id}")
    fun gerService(@Path("id")id :Int): Call<ServicesItem>

}