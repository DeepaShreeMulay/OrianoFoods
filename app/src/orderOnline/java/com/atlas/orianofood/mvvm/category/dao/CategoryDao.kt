package com.atlas.orianofood.mvvm.category.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.atlas.orianofood.mvvm.category.model.CategoryItem

@Dao
interface CategoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertData(categoryItem: CategoryItem): Long

    @Query("SELECT * from CategoryItem")
    fun selectAllData(): MutableList<CategoryItem>
}