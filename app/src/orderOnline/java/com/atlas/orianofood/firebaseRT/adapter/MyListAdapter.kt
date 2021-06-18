package com.atlas.orianofood.firebaseRT.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.atlas.orianofood.R
import com.squareup.picasso.Picasso


class MyListAdapter(listdata: Array<String>) :
    RecyclerView.Adapter<MyListAdapter.ViewHolder>() {
    private val listdata: Array<String> = listdata
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem: View = layoutInflater.inflate(R.layout.offer_card_view, parent, false)
        return ViewHolder(listItem)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val url: String = listdata[position]


        try {
            Picasso.get()
                .load(url)
                .placeholder(com.atlas.orianofood.R.drawable.ic_img_placeholder)
                .into(holder.imageView)

        } catch (ex: Exception) {

            holder.imageView.setImageResource(com.atlas.orianofood.R.drawable.ic_img_placeholder)

        }

    }

    override fun getItemCount(): Int {
        return listdata.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView: ImageView = itemView.findViewById(R.id.adView)

    }

}