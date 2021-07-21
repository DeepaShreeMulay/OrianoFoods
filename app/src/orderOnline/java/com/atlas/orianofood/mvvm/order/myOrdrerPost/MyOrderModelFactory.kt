package com.atlas.orianofood.mvvm.order.myOrdrerPost

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MyOrderModelFactory(private val myOrderRepository: MyOrderRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MyOrderViewModel(myOrderRepository) as T
    }

}