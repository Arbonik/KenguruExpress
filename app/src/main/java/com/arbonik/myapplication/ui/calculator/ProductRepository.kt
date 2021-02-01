package com.arbonik.myapplication.ui.calculator

import androidx.lifecycle.MutableLiveData
import com.arbonik.myapplication.network.models.ProductRequest
import com.arbonik.myapplication.network.Common
import com.arbonik.myapplication.product.ProductValidator

class ProductRepository{
    fun createCargo(productRequest : ProductRequest) = Common.PRODUCT.createProduct(productRequest).execute().body()

//    suspend fun createCargo(){
//        createCargo(currentProductRequest.value!!)
//    }

    val productValidator = ProductValidator()

    var currentProductRequest : MutableLiveData<ProductRequest> = MutableLiveData(ProductRequest())

}
