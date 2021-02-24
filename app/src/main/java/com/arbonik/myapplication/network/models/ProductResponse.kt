package com.arbonik.myapplication.network.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "productResponses")
data class ProductResponse(
    val comment: String? = "",
    val delivery_type: String? = "doc",
    val height: String? = "0",
    @PrimaryKey(autoGenerate = true)
    val id: Int? = 0,
    val length: String?= "",
    val profile: Int? = null,
    val quantity: Int? = 1,
    val volume: String? = "",
    val volumetric_weight: String? ="",
    val weight: String? ="",
    val width: String? =""
)