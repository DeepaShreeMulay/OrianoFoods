package com.atlas.orianofood.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.atlas.orianofood.interfaces.ItemClickListener

class ProductViewHolder : RecyclerView.ViewHolder, View.OnClickListener {

    var img: ImageView
    var name: TextView
    var rate: TextView
    lateinit var itemClickListener: ItemClickListener

    constructor(itemView: View, img: ImageView, name: TextView, rate: TextView) : super(itemView) {
        this.img = img
        this.name = name
        this.rate = rate
        itemView.setOnClickListener(this)
    }

    fun setitemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }


    override fun onClick(v: View?) {
        itemClickListener.onClick(v!!, adapterPosition, false)
    }


}