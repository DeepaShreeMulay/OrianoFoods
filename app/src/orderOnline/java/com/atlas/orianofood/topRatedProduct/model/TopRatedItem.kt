package com.atlas.orianofood.topRatedProduct.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
@Entity
data class TopRatedItem (
    @PrimaryKey(autoGenerate = true)
    @SerializedName("category_id")
    var categoryId: Int,
    @SerializedName("addons")
    var addons: String?,
    @SerializedName("category")
    var category: String?,

    @SerializedName("coupon")
    var coupon: String?,
    @SerializedName("discount")
    var discount: String?,
    @SerializedName("discounted_selling")
    var discountedSelling: String?,
    @SerializedName("feedback")
    var feedback: String?,
    @SerializedName("image_url")
    var imageUrl: String?,
    @SerializedName("ingredient")
    var ingredient: String?,
    @SerializedName("offer")
    var offer: String?,
    @SerializedName("price")
    var price: String?,
    @SerializedName("product_desc")
    var productDesc: String?,
    @SerializedName("product_id")
    var productId: String?,
    @SerializedName("product_name")
    var productName: String?,
    @SerializedName("rating")
    var rating: String?,
    @SerializedName("selling_price")
    var sellingPrice: String?
        )