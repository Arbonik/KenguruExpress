package com.arbonik.myapplication.ui.order

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.arbonik.myapplication.profile.OrderData

class OrderViewModel : ViewModel() {
    private val _order: MutableLiveData<OrderData> = MutableLiveData()
    var order: LiveData<OrderData> = _order

    fun updateOrderData(orderData: OrderData){
        _order.postValue(orderData)
    }

}