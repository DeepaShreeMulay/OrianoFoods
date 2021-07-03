package com.atlas.orianofood.mvvm.topCategory.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class TopCategoryItem(
        @PrimaryKey(autoGenerate = true)
        @SerializedName("category_code")
        var categoryCode: Int?,
        @SerializedName("category_id")
        var categoryId: String,

        @SerializedName("category_desc")
        var categoryDesc: String?,
        @SerializedName("category_name")
        var categoryName: String?,
        @SerializedName("image_url")
        var imageUrl: String?,
        @SerializedName("parent")
        var parent: Int?
)