package com.arbonik.myapplication.network.references

import com.arbonik.myapplication.network.models.ProductRequest
import com.arbonik.myapplication.network.models.ProductResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface ProductReference {
    //need to tots
    @POST("products/")
    suspend fun createProduct(@Body productRequest: ProductRequest): Call<ProductResponse>

    @PATCH("product/{id}")
    fun updateProduct(@Path("id") id : Int, @Body productRequest: ProductRequest) : Call<ProductResponse>
}