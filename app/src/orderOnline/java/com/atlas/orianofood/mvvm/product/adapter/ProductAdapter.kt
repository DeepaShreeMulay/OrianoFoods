package com.atlas.orianofood.mvvm.product.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.atlas.orianofood.BR.item
import com.atlas.orianofood.R
import com.atlas.orianofood.databinding.ProductItemsBinding
import com.atlas.orianofood.firebaseRT.utils.Common.sendStateChangedBroadCast
import com.atlas.orianofood.firebaseRT.utils.UpdateItemToProductIdMap
import com.atlas.orianofood.firebaseRT.utils.selectedProductIDsList
import com.atlas.orianofood.mvvm.product.model.ProductItems
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton

class ProductAdapter(val context: Context, private var pitems: MutableList<ProductItems> = arrayListOf<ProductItems>())
    : RecyclerView.Adapter<ProductAdapter.ProductHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductHolder {
        val binding = ProductItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ProductHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductHolder, position: Int) {
        holder.onBind(pitems[position])
        val productId = pitems[position].productId
        if (selectedProductIDsList.containsKey(productId)) {
            holder.add_btn.setVisible(false)
            holder.btn_number.setVisible(true)
            holder.btn_number.number = selectedProductIDsList.get(productId).toString()
        }

        holder.add_btn.setOnClickListener {
            /* if (selectedProductIDsList.containsKey(productId)) {
                 if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                     selectedProductIDsList.replace(productId, selectedProductIDsList.get(productId)?.plus(1)
                             ?: 1)
                 } else {
                     val qty = selectedProductIDsList.get(productId)?.plus(1) ?: 1
                     selectedProductIDsList.remove(productId)
                     selectedProductIDsList.put(productId, qty)
                 }
                 Toast.makeText(context, "${selectedProductIDsList.get(productId) ?: 1}", Toast.LENGTH_SHORT).show()
                 // wait ok toprated and topselling kholo
             } else {
                 selectedProductIDsList.put(productId, 1)
                 Toast.makeText(context, "Added to cart", Toast.LENGTH_SHORT).show()
             }*/
            if (UpdateItemToProductIdMap(productId, true))
                Toast.makeText(context, "Added to cart", Toast.LENGTH_SHORT).show()
            else
                Toast.makeText(context, "${selectedProductIDsList.get(productId) ?: 1}", Toast.LENGTH_SHORT).show()

            holder.add_btn.setVisible(false)
            holder.btn_number.setVisible(true)
            holder.btn_number.number = selectedProductIDsList.get(productId).toString()


        }

        holder.btn_number.setOnValueChangeListener { view, oldValue, newValue ->
            if (selectedProductIDsList.containsKey(productId)) {
                if (newValue == 0) {
                    selectedProductIDsList.remove(productId)
                    sendStateChangedBroadCast(context, "UPDATED")
                    holder.add_btn.setVisible(true)
                    view.setVisible(false)
                } else {


                    if (oldValue != newValue) {
                        UpdateItemToProductIdMap(productId, newValue)
                        //changedQuantity[productId] = selectedProductIDsList[productId]!!
                        //  sendStateChangedBroadCast(context,"UPDATED")
                    }
                    Toast.makeText(
                        context,
                        "${selectedProductIDsList.get(productId) ?: 1}",
                        Toast.LENGTH_SHORT
                    ).show()
                    holder.add_btn.setVisible(false)
                    view.setVisible(true)
                    view.number = (selectedProductIDsList.get(productId) ?: 1).toString()
                }
            }
        }


    }


    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount(): Int {
        return pitems.size
    }

    fun updateElegentButton(position: Int) {
        notifyItemChanged(position)
    }

    inner class ProductHolder(productdataBinding: ViewDataBinding) :
        ProductBindingViewHolder<ProductItems>(productdataBinding) {
        lateinit var add_btn: Button
        lateinit var btn_number: ElegantNumberButton
        override fun onBind(productItem: ProductItems): Unit = with(productItem) {
            productdataBinding.setVariable(item, productItem)
            add_btn = itemView.findViewById(R.id.btn_add)
            btn_number = itemView.findViewById(R.id.btn_number)

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

    fun Button.isVisible(): Boolean {
        return visibility == View.VISIBLE
    }

    fun View.isVisible(): Boolean {
        return visibility == View.VISIBLE
    }

    fun Button.setVisible(visible: Boolean) {
        visibility = if (visible) {
            Button.VISIBLE
        } else {
            Button.GONE
        }
    }

    fun View.setVisible(visible: Boolean) {
        visibility = if (visible) {
            Button.VISIBLE
        } else {
            Button.GONE
        }
    }


}
