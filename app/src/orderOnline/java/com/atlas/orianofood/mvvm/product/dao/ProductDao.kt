package com.atlas.orianofood.mvvm.product.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.atlas.orianofood.mvvm.product.model.ProductItems

@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProductData(productItem: ProductItems): Long

    @Query("SELECT * from ProductItems")
    fun selectAllData(): MutableList<ProductItems>
}