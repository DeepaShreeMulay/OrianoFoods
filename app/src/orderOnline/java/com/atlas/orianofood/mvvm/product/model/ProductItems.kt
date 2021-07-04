package com.atlas.orianofood.mvvm.product.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class ProductItems(
        @PrimaryKey(autoGenerate = false)
        @SerializedName("product_id")
        var productId: Int,
        @SerializedName("addons")
        var addons: String?,
        @SerializedName("category")
        var category: String?,
        @SerializedName("category_id")
        var categoryId: String?,
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

        @SerializedName("product_name")
        var productName: String?,
        @SerializedName("rating")
        var rating: String?,
        @SerializedName("selling_price")
        var sellingPrice: String?,

        var quantity: Int
) {

        override fun toString(): String {
                return "ProductItems(productId=$productId, addons=$addons, category=$category, categoryId=$categoryId, coupon=$coupon, discount=$discount, discountedSelling=$discountedSelling, feedback=$feedback, imageUrl=$imageUrl, ingredient=$ingredient, offer=$offer, price=$price, productDesc=$productDesc, productName=$productName, rating=$rating, sellingPrice=$sellingPrice)"
        }
}