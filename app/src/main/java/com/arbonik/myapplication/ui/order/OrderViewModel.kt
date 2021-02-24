package com.arbonik.myapplication.ui.order

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arbonik.myapplication.KenguruApplication
import com.arbonik.myapplication.model.FullRequest
import com.arbonik.myapplication.network.models.tariff.Data
import com.arbonik.myapplication.profile.OrderData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OrderViewModel : ViewModel() {
    private val _order: MutableLiveData<OrderData> = MutableLiveData()
    var order: LiveData<OrderData> = _order

    fun updateOrderData(orderData: OrderData){
        _order.postValue(orderData)
    }

    fun loadTariff(id:String) : MutableLiveData<Data>{
        val ret = MutableLiveData<Data>()
        viewModelScope.launch(Dispatchers.IO) {
            ret.postValue(KenguruApplication.database.tariffDao().getById(id))
        }
        return ret
    }

    fun addRequestToDatabase(fullRequest: FullRequest){
        viewModelScope.launch(Dispatchers.IO) {
            KenguruApplication.productRepository.addTrack(fullRequest)
        }
    }

}