package com.arbonik.myapplication.network.references

import com.arbonik.myapplication.network.models.ProductRequest
import com.arbonik.myapplication.network.models.ProductResponse
import retrofit2.Call
import retrofit2.http.*

interface ProductReference {
    @POST("products/")
    fun createProduct(
        @Body productRequest: ProductRequest
    ): Call<ProductResponse>

    @PATCH("product/{id}")
    fun updateProduct(
        @Path("id") id: Int,
        @Body productRequest: ProductRequest
    ): Call<ProductResponse>
}