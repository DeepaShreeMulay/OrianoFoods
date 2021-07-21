package com.atlas.orianofood.mvvm.order.myOrdrerPost

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import kotlinx.coroutines.launch

class MyOrderViewModel(private val repository: MyOrderRepository) : ViewModel() {

    fun myOrder(jsonObject: JsonObject) {
        viewModelScope.launch {
            repository.MyOrder(jsonObject)
        }
    }

}