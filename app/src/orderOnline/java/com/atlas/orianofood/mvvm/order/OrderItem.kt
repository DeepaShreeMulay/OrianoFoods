package com.atlas.orianofood.mvvm.order

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "add_cart")
data class OrderItem(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")

    var id: Int,
    /* @ColumnInfo(name = "product_id")
      var product_id: String,
      @ColumnInfo(name = "quantity")
      var product_quatity: String?*/

    @SerializedName("selectedProductIDsList")
    var selectedProductList: ArrayList<String>

)