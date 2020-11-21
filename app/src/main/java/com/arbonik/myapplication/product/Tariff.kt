package com.arbonik.myapplication.product

import java.math.BigDecimal

data class Tariff(val companyName: String, val date: String, val cost : BigDecimal, val ratingCompany : Int)

fun instanceTariff(){}