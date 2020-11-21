package com.arbonik.myapplication.ui.calculator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.arbonik.myapplication.geography.Locality
import com.arbonik.myapplication.network.Common
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CalculatorViewModel : ViewModel() {

    val localityFrom = LocalityModel()
    val localityTo = LocalityModel()

//    private val _tarifs = MutableLiveData<>

}
class LocalityModel{
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