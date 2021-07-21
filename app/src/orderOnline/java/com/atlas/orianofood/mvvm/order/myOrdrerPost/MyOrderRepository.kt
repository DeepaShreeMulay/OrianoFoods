package com.atlas.orianofood.mvvm.order.myOrdrerPost

import com.atlas.orianofood.mvvm.order.OrderDao
import com.atlas.orianofood.mvvm.retrofit.RetrofitInstance
import com.google.gson.JsonObject


class MyOrderRepository(private val dao: OrderDao) {
    suspend fun MyOrder(jsonObject: JsonObject) {
        RetrofitInstance.api.myOrder(jsonObject)
    }
}