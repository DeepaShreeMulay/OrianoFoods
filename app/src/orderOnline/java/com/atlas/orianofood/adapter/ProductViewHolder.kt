package com.atlas.orianofood.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.atlas.orianofood.interfaces.ItemClickListener

class ProductViewHolder : RecyclerView.ViewHolder, View.OnClickListener {

    var img: ImageView
    var saleTag: ImageView
    var name: TextView
    var rate: TextView
    var sellingPrice: TextView
    var layout: ConstraintLayout

    lateinit var itemClickListener: ItemClickListener

    constructor(
        itemView: View,
        img: ImageView,
        name: TextView,
        rate: TextView,
        sellingPrice: TextView,
        saleTag: ImageView,
        layout: ConstraintLayout
    ) : super(itemView) {
        this.img = img
        this.name = name
        this.rate = rate
        this.sellingPrice = sellingPrice
        this.saleTag = saleTag
        this.layout = layout
        itemView.setOnClickListener(this)
    }

    fun setitemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }


    override fun onClick(v: View?) {
        itemClickListener.onClick(v!!, adapterPosition, false)
    }


}