package com.atlas.orianofood.mvvm.setProfile

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface SetProfileDao {
    @Insert
    fun insertSetProfile(setProfileItem: SetProfileItem): Long

    @Query("SELECT * FROM set_profile")
    fun getAllProfiles(): MutableList<SetProfileItem>
}