package com.arbonik.myapplication.network

import com.arbonik.myapplication.network.data.DeparturesRequest
import com.arbonik.myapplication.network.data.DeparturesResponce
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface DeparturesReference {
    @POST("departures/")
    fun departuresCreate(@Body departures : DeparturesRequest): Call<DeparturesResponce>
}