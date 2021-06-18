package com.atlas.orianofood.mvvm.product.model

import com.google.gson.annotations.SerializedName

data class ProductData(
        @SerializedName("status")
        var status: Int,
        @SerializedName("list")
        var plist: MutableList<ProductItems>
)