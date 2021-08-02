package com.atlas.orianofood.mvvm.topRatedSelling.adapter

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
import com.atlas.orianofood.databinding.TopSellingItemBinding
import com.atlas.orianofood.firebaseRT.utils.HOMESPACTIVITYCONTEXT
import com.atlas.orianofood.firebaseRT.utils.UpdateItemToProductIdMap
import com.atlas.orianofood.firebaseRT.utils.selectedProductIDsList
import com.atlas.orianofood.mvvm.topRatedSelling.model.SellingItem
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton


class SellingAdapter(
    val context: Context,
    private var sitems: MutableList<SellingItem> = arrayListOf<SellingItem>()
) : RecyclerView.Adapter<SellingAdapter.SellingHolder>() {

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount(): Int {
        return sitems.size
    }

    fun updateElegentButton(position: Int) {
        notifyItemChanged(position)
    }

    override fun onBindViewHolder(holder: SellingHolder, position: Int) {
        holder.onBind(sitems[position])
        val productId = sitems[position].productId
        if (selectedProductIDsList.containsKey(productId)) {
            holder.add_btn.setVisible(false)
            holder.btn_number.setVisible(true)
            holder.btn_number.number = selectedProductIDsList.get(productId).toString()
        } else {
            holder.add_btn.setVisible(true)
            holder.btn_number.setVisible(false)
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

             } else {
                selectedProductIDsList.put(productId, 1)
                Toast.makeText(context, "Added to cart", Toast.LENGTH_SHORT).show()
             }*/

            if (UpdateItemToProductIdMap(context, productId, true))
                Toast.makeText(context, "Added to cart", Toast.LENGTH_SHORT).show()
            else
                Toast.makeText(
                    context,
                    "${selectedProductIDsList.get(productId) ?: 1}",
                    Toast.LENGTH_SHORT
                ).show()

            holder.add_btn.setVisible(false)
            holder.btn_number.setVisible(true)
            holder.btn_number.number = selectedProductIDsList.get(productId).toString()


        }

        holder.btn_number.setOnValueChangeListener { view, oldValue, newValue ->
            if (selectedProductIDsList.containsKey(productId)) {
                if (newValue == 0) {
                    HOMESPACTIVITYCONTEXT = context
                    selectedProductIDsList.remove(productId)
//                    sendStateChangedBroadCast(context, "UPDATED")
                    holder.add_btn.setVisible(true)
                    view.setVisible(false)
                } else {

                    /*if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                        selectedProductIDsList.replace(productId, newValue)
                    } else {
                        selectedProductIDsList.remove(productId)
                        selectedProductIDsList.put(productId, newValue)
                    }*/

                    if (oldValue != newValue) {
                        UpdateItemToProductIdMap(context, productId, newValue)
                        // changedQuantity[productId] = selectedProductIDsList[productId]!!
                        //sendStateChangedBroadCast(context,"UPDATED")
                    }

                   /* Toast.makeText(
                        context,
                        "${selectedProductIDsList.get(productId) ?: 1}",
                        Toast.LENGTH_SHORT
                    ).show()*/
                    holder.add_btn.setVisible(false)
                    view.setVisible(true)
                    view.number = (selectedProductIDsList.get(productId) ?: 1).toString()
                }
            }
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SellingHolder {
        val binding =
            TopSellingItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SellingHolder(binding)
    }

    inner class SellingHolder(sellingDataBinding: ViewDataBinding) :
        SellingBindingViewHolder<SellingItem>(sellingDataBinding) {
        lateinit var add_btn: Button
        lateinit var btn_number: ElegantNumberButton
        override fun onBind(sellingItem: SellingItem): Unit = with(sellingItem) {
            sellingDataBinding.setVariable(item, sellingItem)
            add_btn = itemView.findViewById(R.id.btn_add)
            btn_number = itemView.findViewById(R.id.btn_number)
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

    fun Button.isVisible(): Boolean {
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