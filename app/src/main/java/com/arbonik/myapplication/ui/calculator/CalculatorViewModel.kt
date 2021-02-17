package com.arbonik.myapplication.ui.calculator

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.arbonik.myapplication.KenguruApplication.Companion.departuresRepository
import com.arbonik.myapplication.KenguruApplication.Companion.localityRepository
import com.arbonik.myapplication.KenguruApplication.Companion.productRepository
import com.arbonik.myapplication.model.Product
import com.arbonik.myapplication.model.cargo.CargoType
import com.arbonik.myapplication.network.models.DeparturesRequest
import com.arbonik.myapplication.network.models.ProductRequest
import com.arbonik.myapplication.network.models.ProductResponse
import com.arbonik.myapplication.network.models.geography.LocalityResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CalculatorViewModel(application: Application) : AndroidViewModel(application) {

    private val defaultDeliveryType = CargoType.DOCUMENT
    private val defaultDeliveryCargo = ProductRequest(delivery_type = defaultDeliveryType.type)

    private val _lovality: MutableLiveData<LocalityResponse> = MutableLiveData()
    val localityResponse: LiveData<LocalityResponse> = _lovality

    var startLocalityResponse: MutableLiveData<LocalityResponse?> = MutableLiveData()
    var finishLocalityResponse: MutableLiveData<LocalityResponse?> = MutableLiveData()


    val typeProductLiveData = MutableLiveData<CargoType>(defaultDeliveryType)
    val productLiveData = MutableLiveData<Product>(defaultDeliveryCargo)


    fun cargoSettings(product: Product) {
        if (product.weight.isNotBlank() && product.weight.isNotEmpty())
            if (product.weight.toDouble() > 0.0)
                when (typeProductLiveData.value) {
                    CargoType.DOCUMENT -> {
                        productLiveData.value = product
                    }
                    CargoType.CARGO -> {
                        if (product.length.isNotBlank() && product.width.isNotBlank() && product.height.isNotBlank())
                            if (product.length.toDouble() > 0.0 && product.width.toDouble() > 0.0 && product.height.toDouble() > 0.0) {
                                productLiveData.value = product
                            }
                    }
                }
    }

    val currentCargoes: MutableLiveData<ProductResponse> = MutableLiveData()
    fun createProduct() {
        viewModelScope.launch(Dispatchers.IO)  {
                val req = ProductRequest(
                    height = productLiveData.value?.height.toString(),
                    weight = productLiveData.value?.weight.toString(),
                    length = productLiveData.value?.length.toString(),
                    width = productLiveData.value?.width.toString(),
                    delivery_type = typeProductLiveData.value?.type.toString()
                )
                val value = productRepository.createCargo(req).execute().body()
                Log.d("qweqw", value.toString())
                currentCargoes.postValue(value!!)
            }

    }

   fun downloadTariffs(cargoRequest : ProductResponse) {
        viewModelScope.launch(Dispatchers.IO) {
            val departuresRequest = DeparturesRequest(
                listOf(cargoRequest.id!!),
                true,
                true,
                1,
                2,
                null
            )
            val departuresResponce = departuresRepository.createDeparture(departuresRequest)!!
            departuresRepository.downloadTariffsToDatabase(departuresResponce)
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