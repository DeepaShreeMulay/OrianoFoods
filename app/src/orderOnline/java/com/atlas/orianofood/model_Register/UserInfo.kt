package com.atlas.orianofood.model_Register

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "user_data")
data class UserInfo (
    @ColumnInfo(name ="user_name")
    var name: String,
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name="mobile")
    var mobile: Long,
    @ColumnInfo(name="password")
    var password:String

        )


