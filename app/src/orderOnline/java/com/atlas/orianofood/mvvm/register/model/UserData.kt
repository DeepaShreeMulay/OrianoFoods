package com.atlas.orianofood.mvvm.register.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "user_data")
data class UserData(
        @ColumnInfo(name = "user_name")
        var name: String,
        @PrimaryKey(autoGenerate = false)
        @ColumnInfo(name = "mobile")
        var mobile: Long,
        @ColumnInfo(name = "password")
        var password: String,


        )


