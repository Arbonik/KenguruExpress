package com.arbonik.myapplication

import com.arbonik.myapplication.network.Common
import com.arbonik.myapplication.network.data.DeparturesRequest
import org.junit.Test

class ApiTest {
    @Test
    fun ServiceTest(){
        val responce = Common.SERVICES.gerService( 10).execute()
        if(responce.isSuccessful)
        {
            println(responce.body())
        }
        else{
                println("ERROR")
        }
    }

    @Test
    fun DeparturesTest(){
        val responce = Common.DEPARTURES.departuresCreate(
            DeparturesRequest(
                null, true, true, 1,1, null
        )
        ).execute()
        if(responce.isSuccessful)
        {
            println(responce.body())
        }
        else{
                println("ERROR")
        }
    }
}