package com.atlas.orianofood.category.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class CtegoryItem (
        @PrimaryKey(autoGenerate = true)
        @SerializedName("id")
        var id: Int,
        @SerializedName("category_id")
        var categoryId: String,

        @SerializedName("category_code")
        var categoryCode: String,
        @SerializedName("category_desc")
        var categoryDesc: String,

        @SerializedName("category_name")
        var categoryName: String,
        @SerializedName("image_url")
        var imageUrl: String,
        @SerializedName("parent")
        var parent: String
        )
