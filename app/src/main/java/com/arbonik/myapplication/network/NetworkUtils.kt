package com.arbonik.myapplication.network

import com.arbonik.myapplication.model.cargo.Cargo
import com.arbonik.myapplication.network.models.ProductRequest

fun Cargo.toProductRequest(): ProductRequest {
    return ProductRequest(
        delivery_type = this.TYPE.type,
        length = this.length,
        height = this.height,
        comment = this.comment,
        weight = this.weight,
        width = this.width
    )
}