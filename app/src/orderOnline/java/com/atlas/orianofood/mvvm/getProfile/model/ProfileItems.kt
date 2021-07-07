package com.atlas.orianofood.mvvm.getProfile.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class ProfileItems (
        @PrimaryKey(autoGenerate = true)
        @SerializedName("id")
        var id: Int,
        @SerializedName("display_name")
        val displayName: String,
        // @SerializedName("Plan")
        // val plan: Any?,
        // @SerializedName("status")
        // var status: Int,
        @SerializedName("user_email")
        var userEmail: String,
        @SerializedName("user_login")
        var userLogin: String,
        @SerializedName("user_nicename")
        var userNicename: String
        )
{
    override fun toString(): String {
        return "ProfileItems(id=$id, displayName=$displayName, userEmail=$userEmail, userLogin=$userLogin, userNicename=$userNicename)"
    }
}