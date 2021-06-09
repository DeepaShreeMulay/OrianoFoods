package com.atlas.orianofood.mvvm.topRatedSelling.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.atlas.orianofood.mvvm.topRatedSelling.model.SellingItem
@Dao
interface SellingDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSellingData(sellingItem: SellingItem): Long
    @Query("SELECT * FROM SellingItem ")
    fun selectAllData(): MutableList<SellingItem>
}