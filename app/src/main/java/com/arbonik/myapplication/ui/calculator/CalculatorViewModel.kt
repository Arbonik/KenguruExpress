package com.arbonik.myapplication.ui.calculator

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arbonik.myapplication.network.models.DeliveryType
import com.arbonik.myapplication.model.LocalityRepository
import com.arbonik.myapplication.model.Product
import com.arbonik.myapplication.network.models.ProductRequest
import com.arbonik.myapplication.network.models.geography.LocalityResponse
import com.arbonik.myapplication.repositories.CalculateRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CalculatorViewModel : ViewModel() {

    private val defaultDeliveryType = DeliveryType.DOCUMENT
    private val defaultDeliveryCargo = ProductRequest(delivery_type = defaultDeliveryType.type)

    private val localityRepository = LocalityRepository()
    private val calculateRepository = CalculateRepository()
    private val productRepository = ProductRepository()

    private val _lovality: MutableLiveData<LocalityResponse> = MutableLiveData()
    val localityResponse: LiveData<LocalityResponse> = _lovality

    var startLocalityResponse: MutableLiveData<LocalityResponse?> = MutableLiveData()
    var finishLocalityResponse: MutableLiveData<LocalityResponse?> = MutableLiveData()


    val typeProductLiveData = MutableLiveData<DeliveryType>(defaultDeliveryType)
    val productLiveData = MutableLiveData<Product>(defaultDeliveryCargo)

    val tarrifs: LiveData<String> = calculateRepository.tariffs

    fun cargoSettings(product: Product) {
        if (product.weight.isNotBlank() && product.weight.isNotEmpty())
            if (product.weight.toDouble() > 0.0)
                when (typeProductLiveData.value) {
                    DeliveryType.DOCUMENT -> {
                        productLiveData.value = product
                    }
                    DeliveryType.CARGO -> {
                        if (product.length.isNotBlank() && product.width.isNotBlank() && product.height.isNotBlank())
                            if (product.length.toDouble() > 0.0 && product.width.toDouble() > 0.0 && product.height.toDouble() > 0.0) {
                                productLiveData.value = product
                            }
                    }
                }
    }

    fun createProduct() {
        viewModelScope.launch(Dispatchers.IO ) {
            val req = ProductRequest(
                height = productLiveData.value?.height.toString(),
                weight = productLiveData.value?.weight.toString(),
                length = productLiveData.value?.length.toString(),
                width = productLiveData.value?.width.toString(),
                delivery_type = typeProductLiveData.value?.type.toString()
            )
            val result = productRepository.createCargo(req)
        }
    }

    fun downloadTariffs() {
//        val departuresRequest = DeparturesRequest(
//
//        )
        val departuresResponce = calculateRepository.createDeparture()
        viewModelScope.launch(Dispatchers.IO) {
            calculateRepository.downloadTariffs()
        }
    }

    fun setupStartLocality(fullName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = localityRepository.oneLocationSearch(fullName)
            startLocalityResponse.postValue(result)
        }
    }

    fun setupFinishLocality(fullName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = localityRepository.oneLocationSearch(fullName)
            finishLocalityResponse.postValue(result)
        }
    }

    fun localitySearch(locality: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = localityRepository.localitySearch(locality)
            _lovality.postValue(result)
        }
    }
}