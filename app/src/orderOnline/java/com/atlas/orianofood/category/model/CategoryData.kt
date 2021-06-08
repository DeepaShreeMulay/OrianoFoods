package com.atlas.orianofood.category.model

import com.google.gson.annotations.SerializedName

data class CategoryData(
        @SerializedName("status")
        var status: Int,
        @SerializedName("list")
        var list: MutableList<CtegoryItem>

)

