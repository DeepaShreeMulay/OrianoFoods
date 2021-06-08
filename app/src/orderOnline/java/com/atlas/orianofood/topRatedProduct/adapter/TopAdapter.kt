package com.atlas.orianofood.topRatedProduct.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.atlas.orianofood.topRatedProduct.model.TopRatedItem
import com.atlas.orianofood.BR.item

import com.atlas.orianofood.databinding.TopRatedItemsBinding

class TopAdapter (private var titems: MutableList<TopRatedItem> = arrayListOf<TopRatedItem>())
    : RecyclerView.Adapter<TopAdapter.TopHolder>() {

    override fun getItemCount(): Int {
       return titems.size
    }
    override fun onBindViewHolder(holder: TopHolder, position: Int) {
        holder.onBind(titems[position])
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopHolder {
        val binding  = TopRatedItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TopHolder(binding)
    }


    inner class TopHolder(topRatedDatabinding: ViewDataBinding)
        : TopRatedBindingViewHolder<TopRatedItem>(topRatedDatabinding)  {
        override fun onBind(topRatedItem: TopRatedItem): Unit = with(topRatedItem) {
            topRatedDatabinding.setVariable(item, topRatedItem)


        }
    }
    fun add(tresult: MutableList<TopRatedItem>) {
        titems.addAll(tresult)
        notifyDataSetChanged()
    }

    fun clear() {
        titems.clear()
        notifyDataSetChanged()
    }

}