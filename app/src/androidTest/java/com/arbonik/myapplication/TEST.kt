package com.arbonik.myapplication

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.arbonik.myapplication.repositories.CalculateRepository
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TEST {
    val calc = CalculateRepository()
    @Test
    fun calcWebSocket(){
        calc.toCityId = 1
        calc.fromCityId = 1
        calc.calculate()
        while (true){

        }
    }
}