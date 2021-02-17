package com.arbonik.myapplication.network.models

import com.arbonik.myapplication.model.Product
import com.arbonik.myapplication.model.cargo.Cargo

data class ProductRequest(
    val delivery_type: String? = "",
    override var length: String = "",
    val profile: Int? = null,
    val quantity: Int = 1,
    override var height: String = "",
    override var weight: String = "",
    override var width: String = "",
    val comment: String? = "No comment",
) : Product
