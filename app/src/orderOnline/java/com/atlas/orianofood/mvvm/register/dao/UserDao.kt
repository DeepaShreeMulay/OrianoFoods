package com.atlas.orianofood.mvvm.register.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.atlas.orianofood.mvvm.register.model.UserData

@Dao
interface UserDao {

    @Insert
    fun insertUser(userData: UserData)

    @Query("SELECT * FROM user_data")
    fun readuser(): List<UserData>

}