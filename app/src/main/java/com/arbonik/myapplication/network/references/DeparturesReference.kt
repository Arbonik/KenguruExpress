package com.arbonik.myapplication.network.references

import com.arbonik.myapplication.network.models.DeparturesRequest
import com.arbonik.myapplication.network.models.DeparturesResponce
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface DeparturesReference {
    @POST("departures/")
    fun departuresCreate(@Body departures : DeparturesRequest): Call<DeparturesResponce>
}