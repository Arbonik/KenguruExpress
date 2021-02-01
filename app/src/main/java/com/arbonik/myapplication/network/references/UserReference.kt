package com.arbonik.myapplication.network.references

import com.arbonik.myapplication.network.models.login.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST


//{
//  "email": "admin@admin.com",
//  "id": 24
//}
//
//{
//  "email": "admin@admin.com",
//  "password": "whatthehell"
//}
//
interface UserReference {
    @POST("users/")
    fun createUser(@Body user : UserRequest): Call<UserResponse>

    @POST("users/activation/")
    fun activatedUser(@Body data : UserActivation): Call<UserActivation>

    @POST("users/login/")
    fun authefication(@Body user : UserAuth): Call<UserToken>
//
//    @POST("users/logout/")
//    fun logout(@Body )
}