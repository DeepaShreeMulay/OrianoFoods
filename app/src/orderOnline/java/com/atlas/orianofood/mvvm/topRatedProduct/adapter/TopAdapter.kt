package com.atlas.orianofood.mvvm.topRatedProduct.adapter

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
import com.atlas.orianofood.databinding.TopRatedItemsBinding
import com.atlas.orianofood.firebaseRT.utils.UpdateItemToProductIdMap
import com.atlas.orianofood.firebaseRT.utils.selectedProductIDsList
import com.atlas.orianofood.mvvm.order.OrderDao
import com.atlas.orianofood.mvvm.topRatedProduct.model.TopRatedItem
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton

class TopAdapter(val orderDao: OrderDao, val context: Context, private var titems: MutableList<TopRatedItem> = arrayListOf<TopRatedItem>())
    : RecyclerView.Adapter<TopAdapter.TopHolder>() {

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount(): Int {
        return titems.size
    }

    fun updateElegentButton() {

    }

    override fun onBindViewHolder(holder: TopHolder, position: Int) {
        holder.onBind(titems[position])
        val productId = titems[position].productId

        if (selectedProductIDsList.containsKey(productId)) {
            holder.add_btn.setVisible(false)
            holder.btn_number.setVisible(true)
            holder.btn_number.number = selectedProductIDsList.get(productId).toString()
        }

        holder.add_btn.setOnClickListener {
            /*   if (selectedProductIDsList.containsKey(productId)) {
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
                    holder.add_btn.setVisible(true)
                    view.setVisible(false)
                } else {

                    /*if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                        selectedProductIDsList.replace(productId, newValue)
                    } else {
                        selectedProductIDsList.remove(productId)
                        selectedProductIDsList.put(productId, newValue)
                    }*/

                    if (oldValue != newValue)
                        UpdateItemToProductIdMap(productId, newValue)


                    Toast.makeText(context, "${selectedProductIDsList.get(productId) ?: 1}", Toast.LENGTH_SHORT).show()
                    holder.add_btn.setVisible(false)
                    view.setVisible(true)
                    view.number = (selectedProductIDsList.get(productId) ?: 1).toString()
                }
            }
        }


        /*val orderItem = OrderItem(0,holder.product.productId.toString(), holder.product.productName
                , holder.product.price, "1", holder.product.imageUrl, holder.product.price)
        orderDao.insertOrder(orderItem)
        val intent = Intent(context,MyCartSpActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        context.startActivity(intent)
        Toast.makeText(context, "Adding item to Card Item : " +holder.product,Toast.LENGTH_LONG).show()*/
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopHolder {
        val binding = TopRatedItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TopHolder(binding)
    }


    inner class TopHolder(topRatedDatabinding: ViewDataBinding)
        : TopRatedBindingViewHolder<TopRatedItem>(topRatedDatabinding) {
        lateinit var add_btn: Button
        lateinit var product: TopRatedItem
        lateinit var btn_number: ElegantNumberButton

        override fun onBind(topRatedItem: TopRatedItem): Unit = with(topRatedItem) {
            topRatedDatabinding.setVariable(item, topRatedItem)
            add_btn = itemView.findViewById(R.id.btn_add)
            btn_number = itemView.findViewById(R.id.btn_number)
            product = topRatedItem
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