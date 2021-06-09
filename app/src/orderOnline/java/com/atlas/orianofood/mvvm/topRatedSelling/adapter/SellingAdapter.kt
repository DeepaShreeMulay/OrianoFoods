package com.atlas.orianofood.mvvm.topRatedSelling.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.atlas.orianofood.BR.item
import com.atlas.orianofood.databinding.TopSellingItemBinding
import com.atlas.orianofood.mvvm.topRatedSelling.model.SellingItem


class SellingAdapter (private var sitems: MutableList<SellingItem> = arrayListOf<SellingItem>())
   : RecyclerView.Adapter<SellingAdapter.SellingHolder>() {

   override fun getItemCount(): Int {
      return sitems.size
   }
   override fun onBindViewHolder(holder: SellingHolder, position: Int) {
     holder.onBind(sitems[position])
   }

   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SellingHolder {
      val binding  = TopSellingItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
      return SellingHolder(binding)
   }
   inner class SellingHolder(sellingDataBinding: ViewDataBinding)
      :SellingBindingViewHolder<SellingItem>(sellingDataBinding)  {
      override fun onBind(sellingItem: SellingItem): Unit = with(sellingItem) {
         sellingDataBinding.setVariable(item, sellingItem)
      }
   }
   fun add(sresult: MutableList<SellingItem>) {
      sitems.addAll(sresult)
      notifyDataSetChanged()
   }

   fun clear() {
      sitems.clear()
      notifyDataSetChanged()
   }

}