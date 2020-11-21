package com.arbonik.myapplication.network

import com.arbonik.myapplication.geography.Locality
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GeographyReference {
    @GET("/geo/locality/")
    fun locality(@Query("term") term : String, @Query("count") count : Int): Call<Locality>

}