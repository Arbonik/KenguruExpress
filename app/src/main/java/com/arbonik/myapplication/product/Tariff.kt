package com.arbonik.myapplication.product

import android.util.Log
import androidx.room.Entity
import java.math.BigDecimal
import kotlin.random.Random

data class Tariff(
    val companyName: String = "",
    val date: String = "",
    val cost: BigDecimal = BigDecimal.ONE,
    val ratingCompany: Int = 0
)


fun instanceTariff() = Tariff(
    String(Random.nextBytes(10)),
    String(Random.nextBytes(10)),
    BigDecimal.ONE,
    Random.nextInt(5)
).apply {
    Log.d("TARIFF", "CREQTE")
}
