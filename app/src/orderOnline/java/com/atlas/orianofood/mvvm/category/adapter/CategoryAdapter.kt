package com.atlas.orianofood.mvvm.category.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.atlas.orianofood.BR.item
import com.atlas.orianofood.databinding.CategoryItemBinding
import com.atlas.orianofood.mvvm.category.ShowCategoryData
import com.atlas.orianofood.mvvm.category.model.CategoryItem

class CategoryAdapter(private val context: Context, private var items: MutableList<CategoryItem> = arrayListOf<CategoryItem>()) :
        RecyclerView.Adapter<CategoryAdapter.CategoryHolder>() {

    private var transferCategory: ShowCategoryData = context as ShowCategoryData

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: CategoryHolder, position: Int) {
        holder.onBind(items[position])
        holder.itemView.setOnClickListener {
            transferCategory.transferCategoryData(items[position].categoryId)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryHolder {
        val binding =
            CategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryHolder(binding)
    }


    inner class CategoryHolder(dataBinding: ViewDataBinding) :
        CategoryBindingViewHolder<CategoryItem>(dataBinding) {
        override fun onBind(categoryItem: CategoryItem): Unit = with(categoryItem) {
            dataBinding.setVariable(item, categoryItem)


        }
    }

    fun add(result: MutableList<CategoryItem>) {
        items.addAll(result)
        notifyDataSetChanged()
    }

    fun clear() {
        items.clear()
        notifyDataSetChanged()
    }
}