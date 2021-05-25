package com.atlas.orianofood.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.atlas.orianofood.model_Register.LoginInfo

@Dao
interface LoginDao {
    @Insert
    fun insertLogin(loginInfo: LoginInfo)

    @Query(" SELECT * FROM login_data")
    fun readuser():List<LoginInfo>
}