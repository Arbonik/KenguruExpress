package com.arbonik.myapplication

import android.util.Log
import com.arbonik.myapplication.network.Common
import com.arbonik.myapplication.network.models.DeparturesRequest
import com.arbonik.myapplication.network.models.login.UserActivation
import com.arbonik.myapplication.network.models.login.UserRequest
import com.arbonik.myapplication.network.toProductRequest
import com.arbonik.myapplication.product.CargoCreator
import okhttp3.WebSocket
import org.junit.Test

class ApiTest {

    val cargo = CargoCreator.createCargo(1, 1, 1, 1)

    @Test
    fun CreateCargo() {
        val responce = Common.PRODUCT.createProduct(cargo.toProductRequest())

        val a = responce.execute()
        println("asd")
        println(a.body().toString())
    }

    @Test
    fun DeparturesTest() {
        val responce = Common.DEPARTURES.departuresCreate(
            DeparturesRequest(
                listOf(41), true, true, 1, 2, null
            )
        ).execute()
        if (responce.isSuccessful) {
            println(responce.body())
        } else {
            println("ERROR")
        }
    }

    @Test
    fun AccountActivated(){
        val responce = Common.USER.activatedUser(UserActivation("5oc-81af424b959c5e84555c","11")).execute()
        println(responce.body())
        println(responce.code())
        println(responce.isSuccessful)
    }

    @Test
    fun loginText(){
        val response = Common.USER.authefication(UserRequest("kvantpark@gmail.com", "1234Test")).execute()
//        val response = Common.USER.authefication(UserRequest("zmear7@gmail.com", "1234Test")).execute()
        println(response.body().toString())
        println(response.code())
        println(response.isSuccessful)
    }
}