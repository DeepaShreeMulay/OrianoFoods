package com.atlas.orianofood.firebaseRT.adapter

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.atlas.orianofood.R
import com.atlas.orianofood.firebaseRT.model.Subscription
import java.text.NumberFormat
import java.util.*


class SubscriptionAdapter(listdata: Array<Subscription>) :
    RecyclerView.Adapter<SubscriptionAdapter.ViewHolder>() {
    private val listdata: Array<Subscription> = listdata
    val locale = Locale("en", "IN")
    val nf = NumberFormat.getCurrencyInstance(locale)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem: View = layoutInflater.inflate(
            R.layout.subscription_cardview_item,
            parent,
            false
        )
        return ViewHolder(listItem)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data: Subscription = listdata[position]
        holder.planName.text = data.name
        holder.planName.setTextColor(ColorStateList.valueOf(Color.parseColor(data.color)))
        holder.planDesc.text = data.description
        holder.planDesc.setTextColor(ColorStateList.valueOf(Color.parseColor(data.color)))
        holder.planAmount.text = data.amount
        holder.planAmount.setTextColor(ColorStateList.valueOf(Color.parseColor(data.color)))
        holder.planColor.backgroundTintList = ColorStateList.valueOf(Color.parseColor(data.color))
        holder.planCard.backgroundTintList = ColorStateList.valueOf(Color.parseColor(data.color))
    }

    override fun getItemCount(): Int {
        return listdata.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var planCard: CardView = itemView.findViewById(R.id.planCard)
        var planColor: View = itemView.findViewById(R.id.planColor)
        var planName: TextView = itemView.findViewById(R.id.planName)
        var planDesc: TextView = itemView.findViewById(R.id.planDesc)
        var planAmount: TextView = itemView.findViewById(R.id.planAmount)
    }

}