package com.atlas.orianofood.mvvm.topCategory.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.atlas.orianofood.BR.item
import com.atlas.orianofood.databinding.TopCategoryItemBinding
import com.atlas.orianofood.mvvm.activity.ProductSPActivity
import com.atlas.orianofood.mvvm.topCategory.model.TopCategoryItem

class TopCategoryAdapter(private val context: Context, private var tcitems: MutableList<TopCategoryItem> = arrayListOf<TopCategoryItem>())
    : RecyclerView.Adapter<TopCategoryAdapter.TopCategoryHolder>() {

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount(): Int {
        return tcitems.size
    }

    override fun onBindViewHolder(holder: TopCategoryHolder, position: Int) {
        holder.onBind(tcitems[position])
        holder.itemView.setOnClickListener {
            val intent = Intent(context, ProductSPActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            intent.putExtra("CategoryID", tcitems[position].categoryId.toString())

            context.startActivity(intent)

        }

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