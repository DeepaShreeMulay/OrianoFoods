package com.atlas.orianofood.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.atlas.orianofood.model_Register.UserInfo

@Dao
interface UserDao {

    @Insert
    fun insertUser(userInfo: UserInfo)
    @Query("SELECT * FROM user_data")
    fun readuser():List<UserInfo>

}