package com.atlas.orianofood.adapter

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.atlas.orianofood.interfaces.ItemClickListener

class OffersViewHolder : RecyclerView.ViewHolder, View.OnClickListener {

    var img: ImageView
    lateinit var itemClickListener: ItemClickListener

    constructor(itemView: View, img: ImageView) : super(itemView) {
        this.img = img
        itemView.setOnClickListener(this)
    }

    fun setitemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }


    override fun onClick(v: View?) {
        itemClickListener.onClick(v!!, adapterPosition, false)
    }


}