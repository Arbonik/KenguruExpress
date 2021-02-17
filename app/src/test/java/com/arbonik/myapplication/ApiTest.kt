package com.arbonik.myapplication

import android.util.Log
import com.arbonik.myapplication.network.Common
import com.arbonik.myapplication.network.models.DeparturesRequest
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

}