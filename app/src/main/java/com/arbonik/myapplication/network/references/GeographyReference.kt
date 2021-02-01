package com.arbonik.myapplication.network.references

import com.arbonik.myapplication.network.models.geography.LocalityResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GeographyReference {
    @GET("/geo/locality/")
    suspend fun locality(@Query("term") term : String, @Query("count") count : Int) : LocalityResponse

}