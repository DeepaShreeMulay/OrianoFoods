package com.atlas.orianofood.mvvm.login.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "login_data")
data class LoginData(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "userId")
    @SerializedName("user-id") var userId: Int?,
    @ColumnInfo(name = "token")
    @SerializedName("token") var token: String?,
    @ColumnInfo(name = "login_password")
    @SerializedName("pwd") var passwordlogin: String?,
    @ColumnInfo(name = "login_mobile")
    @SerializedName("mobile") var mobilelogin: Long?
)