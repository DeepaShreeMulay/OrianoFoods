package com.atlas.orianofood.mvvm.product.dao

import androidx.room.*
import com.atlas.orianofood.mvvm.product.model.ProductItems

@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProductData(productItem: ProductItems): Long

    @Query("SELECT * from ProductItems")
    fun selectAllData(): MutableList<ProductItems>

    @Query("SELECT * from ProductItems where categoryId = :categoryID")
    fun selectDataByCategoryId(categoryID: String): MutableList<ProductItems>

    @Update
    fun updateProduct(product: ProductItems)

    @Query("SELECT * from ProductItems where productId in (:ids)")
    fun selectDataByProductIdList(ids: List<Int>): MutableList<ProductItems>

}