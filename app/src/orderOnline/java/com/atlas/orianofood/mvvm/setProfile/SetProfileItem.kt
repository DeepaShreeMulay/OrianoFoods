package com.atlas.orianofood.mvvm.setProfile

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "set_profile")
data class SetProfileItem(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int,
    @ColumnInfo(name = "user_name")
    var user_name: String,
    @ColumnInfo(name = "user_email")
    var user_email: String?,
    @ColumnInfo(name = "user_mobile")
    var user_mobile: String?,
    @ColumnInfo(name = "user_address")
    var user_address: String,
    /* @ColumnInfo(name = "change_password")
     var user_password:String*/
)