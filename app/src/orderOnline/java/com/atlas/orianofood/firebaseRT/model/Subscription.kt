package com.atlas.orianofood.firebaseRT.model

import com.google.gson.annotations.SerializedName

data class Subscription(
    @SerializedName("id")
    var id: Int,
    @SerializedName("name")
    var name: String,
    @SerializedName("amount")
    var amount: String,
    @SerializedName("duration")
    var duration: String,
    @SerializedName("plan")
    var plan: String,
    @SerializedName("color")
    var color: String,
    @SerializedName("description")
    var description: String
)