package com.atlas.orianofood.mvvm.product.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.atlas.orianofood.BR.item
import com.atlas.orianofood.databinding.ProductItemsBinding
import com.atlas.orianofood.mvvm.product.model.ProductItems

class ProductAdapter(private var pitems: MutableList<ProductItems> = arrayListOf<ProductItems>())
    : RecyclerView.Adapter<ProductAdapter.ProductHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductHolder {
        val binding = ProductItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductHolder, position: Int) {
        holder.onBind(pitems[position])


    }

    override fun getItemCount(): Int {
        return pitems.size
    }

    inner class ProductHolder(productdataBinding: ViewDataBinding)
        : ProductBindingViewHolder<ProductItems>(productdataBinding) {
        override fun onBind(productItem: ProductItems): Unit = with(productItem) {
            productdataBinding.setVariable(item, productItem)


        }
    }

    fun add(presult: MutableList<ProductItems>) {
        pitems.addAll(presult)
        notifyDataSetChanged()
    }

    fun clear() {
        pitems.clear()
        notifyDataSetChanged()
    }

}