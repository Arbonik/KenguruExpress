package com.arbonik.myapplication.product

data class ProductResponse(
    val comment: String?,
    val delivery_type: String?,
    val height: String?,
    val id: Int?,
    val length: String?,
    val profile: Int?,
    val quantity: Int?,
    val volume: String?,
    val volumetric_weight: String?,
    val weight: String?,
    val width: String?
)