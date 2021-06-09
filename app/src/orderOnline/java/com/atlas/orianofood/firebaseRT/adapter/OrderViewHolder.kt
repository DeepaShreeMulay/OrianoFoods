package com.atlas.orianofood.firebaseRT.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.atlas.orianofood.firebaseRT.interfaces.ItemClickListener

class OrderViewHolder : RecyclerView.ViewHolder, View.OnClickListener {

    var id: TextView
    var status: TextView
    var orderitems: TextView
    var addressline1: TextView
    var date: TextView
    var total: TextView

    lateinit var itemClickListener: ItemClickListener

    constructor(
        itemView: View,
        id: TextView,
        status: TextView,
        orderitems: TextView,
        addressline1: TextView,
        date: TextView,
        total: TextView
    ) : super(itemView) {

        this.id = id
        this.status = status
        this.orderitems = orderitems
        this.addressline1 = addressline1
        this.date = date
        this.total = total

        itemView.setOnClickListener(this)

    }

    fun setitemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }


    override fun onClick(v: View?) {
        itemClickListener.onClick(v!!, adapterPosition, false)
    }


}