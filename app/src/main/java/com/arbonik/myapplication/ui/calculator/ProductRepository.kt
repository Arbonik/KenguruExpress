package com.arbonik.myapplication.ui.calculator

import androidx.lifecycle.MutableLiveData
import com.arbonik.myapplication.KenguruApplication
import com.arbonik.myapplication.model.FullRequest
import com.arbonik.myapplication.network.Common
import com.arbonik.myapplication.network.models.ProductRequest
import com.arbonik.myapplication.network.models.ProductResponse

class ProductRepository{
    fun createCargo(productRequest : ProductRequest) = Common.PRODUCT.createProduct(productRequest)
    fun addTrack(fullRequest: FullRequest) {
        KenguruApplication.database.tariffDao().insert(fullRequest)
    }
    fun getAllRequests():List<FullRequest>{

        return KenguruApplication.database.tariffDao().getAllRequested()
    }
    var currentProductResponse: ProductResponse? = null
}
