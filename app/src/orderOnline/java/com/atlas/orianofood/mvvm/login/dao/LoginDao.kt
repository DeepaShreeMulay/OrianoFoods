package com.atlas.orianofood.mvvm.login.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.atlas.orianofood.mvvm.login.model.LoginData

@Dao
interface LoginDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLogin(loginData: LoginData)

    @Query(" SELECT * FROM login_data")
    fun readuser(): List<LoginData>

    @Query("UPDATE login_data set login_password = :newPassword where login_mobile = :oldPassword")
    fun updatePassword(oldPassword: String, newPassword: String)


    /*@Query("SELECT * from login_data WHERE userId= :userId")
    fun getUserById(userId: Int): List<LoginInfo?>?

    @Query("UPDATE login_data SET token = :token WHERE userId = :userId")
    fun updateLogin(userId: Int, token: String)*/

    /*fun insertOrUpdate(loginInfo: LoginInfo) {
        val list: List<LoginInfo?>? = loginInfo.userId?.let { getUserById(it) }
        if (list != null) {
            if (list.isEmpty()) {
                insertLogin(loginInfo)
            } else {
                loginInfo.token?.let { updateLogin(loginInfo.userId!!, it) }
            }
        } else {
            loginInfo.token?.let { updateLogin(loginInfo.userId!!, it) }
        }
    }*/

}