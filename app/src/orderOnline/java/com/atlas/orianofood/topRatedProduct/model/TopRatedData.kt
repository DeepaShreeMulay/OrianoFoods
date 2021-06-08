package com.atlas.orianofood.topRatedProduct.model

import com.google.gson.annotations.SerializedName

data class TopRatedData (
    @SerializedName("status")
    var status: Int,
    @SerializedName("list")
    var rlist:MutableList<TopRatedItem>
        )