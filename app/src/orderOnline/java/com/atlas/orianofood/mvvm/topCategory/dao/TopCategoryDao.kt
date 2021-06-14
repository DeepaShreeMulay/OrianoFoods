package com.atlas.orianofood.mvvm.topCategory.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.atlas.orianofood.mvvm.topCategory.model.TopCategoryItem

@Dao
interface TopCategoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTopCategoryData(topCategoryItem: TopCategoryItem): Long
    @Query("SELECT * FROM TopCategoryItem ")
    fun selectAllData(): MutableList<TopCategoryItem>
}