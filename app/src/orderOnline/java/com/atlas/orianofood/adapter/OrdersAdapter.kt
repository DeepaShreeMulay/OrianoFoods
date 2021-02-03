package com.atlas.orianofood.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.atlas.orianofood.R
import com.atlas.orianofood.model.Order


class OrdersAdapter(private val context: Context, private val orderlist: List<Order>) :
    RecyclerView.Adapter<OrderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.order_item, parent, false)
        val id = view.findViewById<TextView>(R.id.tv_order_id)
        val status = view.findViewById<TextView>(R.id.tv_status)
        val orderitems = view.findViewById<TextView>(R.id.tv_orderitems)
        val addressline1 = view.findViewById<TextView>(R.id.tv_addressline1)
        val date = view.findViewById<TextView>(R.id.tv_date)
        val total = view.findViewById<TextView>(R.id.tv_total)
        return OrderViewHolder(view, id, status, orderitems, addressline1, date, total)
    }

    override fun getItemCount(): Int {
        return orderlist.count()
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {

        holder.id.text = orderlist[position].orderId
        holder.date.text = orderlist[position].orderDate
        holder.status.text = orderlist[position].orderStatus
        holder.total.text = orderlist[position].orderTotalAmount
        holder.orderitems.text = orderlist[position].orderItems
        holder.addressline1.text = orderlist[position].orderDeliverAt

    }

}