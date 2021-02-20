package com.arbonik.myapplication.ui.calculator

import androidx.lifecycle.MutableLiveData
import com.arbonik.myapplication.model.LocalityPair
import com.arbonik.myapplication.network.models.ProductRequest
import com.arbonik.myapplication.network.Common
import com.arbonik.myapplication.network.models.ProductResponse
import com.arbonik.myapplication.network.models.tariff.Data
import com.arbonik.myapplication.product.ProductValidator
import com.arbonik.myapplication.profile.OrderData
import com.arbonik.myapplication.profile.Profile

class ProductRepository{
    fun createCargo(productRequest : ProductRequest) = Common.PRODUCT.createProduct(productRequest)

    val fullRequest = FullRequest()

    val productValidator = ProductValidator()

    var currentProductRequest : MutableLiveData<ProductRequest> = MutableLiveData(ProductRequest())


}

// класс, с помощью которого формируется конечная заявка, при неоформлении она записывается в бд, чтобы менеджер мог проаналоизировать
class FullRequest {
    var localityPair : LocalityPair? = null
    var cargo : ProductResponse? = null
    var tariff : Data? = null
    var sender : Profile? = null
    var receiver : OrderData? = null
}