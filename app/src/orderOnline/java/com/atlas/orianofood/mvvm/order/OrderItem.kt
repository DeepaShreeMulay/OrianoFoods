package com.atlas.orianofood.mvvm.order

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "add_cart")
data class OrderItem(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id")
        var id: Int,
        @ColumnInfo(name = "product_id")
        var product_id: Int,
        @ColumnInfo(name = "product_name")
        var product_name: String?,
        @ColumnInfo(name = "product_price")
        var price: String?,
        @ColumnInfo(name = "quantity")
        var product_quatity: String?,
        /* @ColumnInfo(name="image_url")
         var img_url:String?,*/
        @ColumnInfo(name = "final_price")
        var pfinalprice: String?

)