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

    @Query("UPDATE ProfileItems set userAltEmail = :mail where userEmail = :phone")
    fun addEmail(phone: String, mail: String)

    @Query("UPDATE ProfileItems set userEmail = :newEmail where userEmail = :oldEmail")
    fun updateEmail(oldEmail: String, newEmail: String)


    /* @Query("Select COUNT(*) FROM ProductItems")
     fun getCount(): Int*/
}