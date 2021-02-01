package com.arbonik.myapplication.network.models

import com.arbonik.myapplication.model.Product

data class ProductRequest (
    val comment: String? = "",
    val delivery_type: String? = "",
    override var lenght: String = "",
    val profile: Int? = 0,
    val quantity: Int? = 1,
    override var height: String = "",
    override var weight: String = "",
    override var width: String = ""
): Product

enum class DeliveryType(val type : String){
    DOCUMENT("doc"),
    CARGO("cargo")
}