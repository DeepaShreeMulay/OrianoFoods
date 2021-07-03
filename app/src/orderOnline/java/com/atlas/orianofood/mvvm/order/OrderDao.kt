package com.atlas.orianofood.mvvm.order

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface OrderDao {
    @Insert
    fun insertOrder(orderItem: OrderItem): Long

    @Update
    suspend fun updateOrder(orderItem: OrderItem)

    @Query("SELECT * FROM add_cart")
    fun getAllOrder(): MutableList<OrderItem>

}