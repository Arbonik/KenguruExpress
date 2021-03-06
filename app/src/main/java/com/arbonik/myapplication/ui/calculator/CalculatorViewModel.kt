package com.arbonik.myapplication.ui.calculator

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
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

    private val _locality: MutableLiveData<LocalityResponse> = MutableLiveData()
    val localityResponse: LiveData<LocalityResponse> = _locality

    val typeProductLiveData = MutableLiveData<CargoType>(defaultDeliveryType)
    val productLiveData = MutableLiveData<Product>(defaultDeliveryCargo)

    //Обновляется при создании груза с сервера
    val currentCargoes: MutableLiveData<ProductResponse> = MutableLiveData()

    fun isLocalitySetup() : Boolean{
        return localityRepository.localityResponseToLocalityPair().fromCityId != null
                && localityRepository.localityResponseToLocalityPair().toCityId != null
    }

    fun createCargo() {
        viewModelScope.launch(Dispatchers.IO) {
            val req = ProductRequest(
                height = productLiveData.value?.height.toString(),
                weight = productLiveData.value?.weight.toString(),
                length = productLiveData.value?.length.toString(),
                width = productLiveData.value?.width.toString(),
                delivery_type = typeProductLiveData.value?.type.toString()
            )
            val value = productRepository.createCargo(req).execute().body()
            productRepository.currentProductResponse = value
            currentCargoes.postValue(value!!)
        }
    }

    //Ищет тарифы для указанного груза и скачивает их в базу данных
    fun downloadTariffs(cargoRequest: ProductResponse) {
        viewModelScope.launch(Dispatchers.IO) {
            val localityPair = localityRepository.localityResponseToLocalityPair()
            val departuresRequest = DeparturesRequest(
                listOf(cargoRequest.id!!),
                true,
                true,
                localityPair.fromCityId ?: 0,
                localityPair.toCityId ?: 0,
                null
            )
            val departuresResponce = departuresRepository.createDeparture(departuresRequest)!!
            departuresRepository.downloadTariffsToDatabase(departuresResponce)
        }
    }

    fun setupStartLocality(fullName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = localityRepository.oneLocationSearch(fullName)
            localityRepository.localityFrom = result
        }
    }

    fun setupFinishLocality(fullName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = localityRepository.oneLocationSearch(fullName)
            localityRepository.localityTo = result
        }
    }

    fun localitySearch(locality: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = localityRepository.localitySearch(locality)
            _locality.postValue(result)
        }
    }

    fun cargoSetSettings(product: Product = productLiveData.value!!): Boolean {
        if (hasValue(product.weight))
            when (typeProductLiveData.value) {
                CargoType.DOCUMENT -> {
                    productLiveData.value = product
                    return true
                }
                CargoType.CARGO -> {
                    if (hasValue(product.length) && hasValue(product.width) && hasValue(product.height)) {
                        productLiveData.value = product
                        return true
                    } else return false
                }
            }
        return false
    }

    private fun hasValue(value: String) = try {
        value.isNotBlank() && value.isNotEmpty() && (value.toDouble() > 0)
    } catch (e: NumberFormatException) {
        false
    }

}