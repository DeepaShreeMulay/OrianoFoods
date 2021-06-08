package com.atlas.orianofood.topRatedSelling.model

import com.google.gson.annotations.SerializedName

data class SellingData (
    @SerializedName("status")
    var status: Int,
    @SerializedName("list")
    var slist: MutableList<SellingItem>
        )