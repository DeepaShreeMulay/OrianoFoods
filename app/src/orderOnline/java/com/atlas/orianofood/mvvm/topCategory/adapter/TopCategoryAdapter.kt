package com.atlas.orianofood.mvvm.topCategory.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.atlas.orianofood.mvvm.topCategory.model.TopCategoryItem
import com.atlas.orianofood.BR.item
import com.atlas.orianofood.databinding.TopCategoryItemBinding

class TopCategoryAdapter (private var tcitems: MutableList<TopCategoryItem> = arrayListOf<TopCategoryItem>())
    : RecyclerView.Adapter<TopCategoryAdapter.TopCategoryHolder>() {

    override fun getItemCount(): Int {
        return tcitems.size
    }
    override fun onBindViewHolder(holder: TopCategoryHolder, position: Int) {
        holder.onBind(tcitems[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopCategoryHolder {
        val binding  = TopCategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TopCategoryHolder(binding)
    }
     inner class TopCategoryHolder(topCategoryDataBinding: ViewDataBinding)
        :TopCategoryBindingViewHolder<TopCategoryItem>(topCategoryDataBinding)  {
        override fun onBind(topCategoryItem: TopCategoryItem): Unit = with(topCategoryItem) {
            topCategoryDataBinding.setVariable(item, topCategoryItem)


        }
    }
    fun add(tcresult: MutableList<TopCategoryItem>) {
        tcitems.addAll(tcresult)
        notifyDataSetChanged()
    }

    fun clear() {
        tcitems.clear()
        notifyDataSetChanged()
    }
}