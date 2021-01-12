package com.arbonik.myapplication

import android.location.Location
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.arbonik.myapplication.network.data.geography.instanceEmptyLocalityItem
import com.arbonik.myapplication.product.Calculator
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TEST {
    val calc = Calculator()
    @Test
    fun calcWebSocket(){
        calc.toIdSity = 1
        calc.fromIdSity = 1
        calc.calculate()
        while (true){

        }
    }
}