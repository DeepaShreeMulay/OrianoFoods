package com.atlas.orianofood.mvvm.topRatedProduct.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.atlas.orianofood.mvvm.product.model.ProductItems
import com.atlas.orianofood.mvvm.topRatedProduct.model.TopRatedItem

@Dao
interface TopRatedDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTopRatedData(topRatedItem: TopRatedItem): Long

    @Query("SELECT * FROM TopRatedItem ")
    fun selectAllData(): MutableList<TopRatedItem>

    @Query("SELECT p.* FROM ProductItems p left join TopRatedItem t on p.productId=t.productId")
    fun getProduct(): MutableList<ProductItems>
}