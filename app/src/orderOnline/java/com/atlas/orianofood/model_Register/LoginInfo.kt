package com.atlas.orianofood.model_Register

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName="login_data")
data class LoginInfo(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name="user-id")

        @SerializedName("user-id")var userId:Int,
        @ColumnInfo(name="token")
        @SerializedName("token")var token:String,
        @ColumnInfo(name="login_password")
        @SerializedName("pwd") var passwordlogin:String?,
        @ColumnInfo(name="login_mobile")
        @SerializedName("mobile")var mobilelogin: Long?


)