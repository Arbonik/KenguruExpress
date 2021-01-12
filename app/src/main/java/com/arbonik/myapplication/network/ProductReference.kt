package com.arbonik.myapplication.network

import com.arbonik.myapplication.model.Product
import com.arbonik.myapplication.network.data.ProductResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface ProductReference {
    //need to tots
    @POST("products/")
    fun createProduct(@Body product: Product): Call<ProductResponse>

    @PATCH("product/{id}")
    fun updateProduct(@Path("id") id : Int, @Body product: Product) : Call<ProductResponse>
}