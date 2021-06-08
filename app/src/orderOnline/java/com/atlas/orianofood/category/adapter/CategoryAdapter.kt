package com.atlas.orianofood.category.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.atlas.orianofood.category.model.CtegoryItem
import com.atlas.orianofood.BR.item
import com.atlas.orianofood.databinding.CategoryItemBinding

class CategoryAdapter (private var items: MutableList<CtegoryItem> = arrayListOf<CtegoryItem>())
    : RecyclerView.Adapter<CategoryAdapter.CategoryHolder>() {

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: CategoryHolder, position: Int) {
        holder.onBind(items[position])
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryHolder {
        val binding  = CategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryHolder(binding)
    }


    inner class CategoryHolder(dataBinding: ViewDataBinding)
        : CategoryBindingViewHolder<CtegoryItem>(dataBinding)  {
        override fun onBind(categoryItem: CtegoryItem): Unit = with(categoryItem) {
            dataBinding.setVariable(item, categoryItem)


        }
    }
    fun add(result: MutableList<CtegoryItem>) {
        items.addAll(result)
        notifyDataSetChanged()
    }

    fun clear() {
        items.clear()
        notifyDataSetChanged()
    }
}