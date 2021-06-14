package com.atlas.orianofood.mvvm.topCategory.model


import com.google.gson.annotations.SerializedName

data class TopCategoryData (
        @SerializedName("status")
        var status: Int,
        @SerializedName("list")
        var tclist:MutableList<TopCategoryItem>
        )