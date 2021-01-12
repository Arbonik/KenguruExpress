package com.arbonik.myapplication.ui.calculator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.arbonik.myapplication.network.data.geography.Locality
import com.arbonik.myapplication.network.Common
import com.arbonik.myapplication.product.Tariff
import com.arbonik.myapplication.product.instanceTariff
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class   CalculatorViewModel : ViewModel() {
    val localityFrom = LocalityRepository()
    val localityTo = LocalityRepository()

//    var idSityRecepient = localityTo.address.

    private val _tariffs = MutableLiveData<MutableList<Tariff>>(mutableListOf())

    val tariffs : LiveData<MutableList<Tariff>> = _tariffs

    init {
        for (i in 0..10) {
            addTarifItem(instanceTariff())
        }
    }
    fun addTarifItem(t : Tariff){
        _tariffs.postValue(_tariffs.value?.apply {
                add(t)
            })

    }
}


class LocalityRepository{
    val COUNT_IN_REQUEST = 10

    private val _address = MutableLiveData<Locality>()
    val address: LiveData<Locality> = _address

    fun localitySearch(term: String) {
        Common.GEOGRAPHY.locality(term, COUNT_IN_REQUEST)
                .enqueue(object : Callback<Locality> {
                    override fun onResponse(call: Call<Locality>, response: Response<Locality>) {
                        _address.postValue(response.body())
                    }
                    override fun onFailure(call: Call<Locality>, t: Throwable) {

                    }
                })
    }
}