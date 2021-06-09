package com.atlas.orianofood.firebaseRT.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.atlas.orianofood.firebaseRT.interfaces.ItemClickListener

class AddressViewHolder : RecyclerView.ViewHolder, View.OnClickListener {

    var type: TextView
    var isDefault: TextView
    var name: TextView
    var addressline1: TextView
    var addressline2: TextView

    lateinit var itemClickListener: ItemClickListener

    constructor(
        itemView: View,
        type: TextView,
        isDefault: TextView,
        name: TextView,
        addressline1: TextView,
        addressline2: TextView
    ) : super(itemView) {

        this.type = type
        this.isDefault = isDefault
        this.name = name
        this.addressline1 = addressline1
        this.addressline2 = addressline2

        itemView.setOnClickListener(this)

    }

    fun setitemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }


    override fun onClick(v: View?) {
        itemClickListener.onClick(v!!, adapterPosition, false)
    }


}