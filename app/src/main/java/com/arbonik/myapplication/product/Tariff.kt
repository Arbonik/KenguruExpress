package com.arbonik.myapplication.product

import android.util.Log
import java.math.BigDecimal
import kotlin.random.Random

data class Tariff(val companyName: String, val date: String, val cost : BigDecimal, val ratingCompany : Int)

fun instanceTariff() = Tariff(
        String(Random.nextBytes(10)),
        String(Random.nextBytes(10)),
        BigDecimal.ONE,
        Random.nextInt(5)
).apply {
    Log.d("TARIFF", "CREQTE")
}
