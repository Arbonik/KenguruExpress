package com.arbonik.myapplication.network.models.tariff

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "deliveryData")
data class Data(
    val common_price: String? = "",
    val delivery: Boolean? = false,
    val delivery_day: String? = "",
    @PrimaryKey(autoGenerate = false)
    val id: Int? = 0,
    val logo: String? = "",
    val `operator`: String? = "",
    val pickup: Boolean? = false,
    val pickup_day: String? = "",
    val price: Int? = 0,
    val rating: Int? = 0,
    val term: String? = "",
    val title: String? = ""
)