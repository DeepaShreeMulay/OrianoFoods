package com.atlas.orianofood.category.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.atlas.orianofood.category.model.CtegoryItem
@Dao
interface CategoryDao  {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertData(categoryItem: CtegoryItem): Long
    @Query("SELECT * from CtegoryItem")
    fun selectAllData(): MutableList<CtegoryItem>
}