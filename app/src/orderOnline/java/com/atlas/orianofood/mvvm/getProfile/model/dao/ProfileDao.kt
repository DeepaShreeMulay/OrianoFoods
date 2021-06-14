package com.atlas.orianofood.mvvm.getProfile.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.atlas.orianofood.mvvm.getProfile.model.ProfileItems

@Dao
interface ProfileDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProfileData(profileItems: ProfileItems): Long
    @Query("SELECT * FROM ProfileItems ")
    fun selectAllData(): MutableList<ProfileItems>
}