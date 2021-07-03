package com.atlas.orianofood.mvvm.order

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.atlas.orianofood.R
import com.atlas.orianofood.core.App
import com.atlas.orianofood.firebaseRT.utils.selectedProductIDsList
import com.atlas.orianofood.mvvm.activity.MyCartSpActivity
import com.atlas.orianofood.mvvm.database.AppDatabase
import com.atlas.orianofood.mvvm.product.model.ProductItems
import kotlinx.android.synthetic.main.activity_my_cart.*
import java.util.*

class OrderAdapter(val context: Context, orderList: HashMap<Int, Int>) : RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {


    private var quantity = 0
    private var coast = 0.0
    private var finalCoast = 0.0
    private val productdao = AppDatabase.getInstance(App.appContext)?.productDao!!

    private lateinit var item: ArrayList<ProductItems>

    init {

        item = productdao.selectDataByProductIdList(orderList.keys.toList()) as ArrayList<ProductItems>

    }//btn change nhi hue add krne p elegent btn nhi aara product me se add karo home pe code nhi he
    //haan aaya pr elegent p click krne p product cart me quantity me change nhi aaya wo code karna he


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        var orderview = LayoutInflater.from(parent.context).inflate(R.layout.my_cart_item, parent, false)
        return OrderViewHolder(orderview)
    }


    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {

        holder.orderProductName.text = item[position].productName
        holder.orderPrice.text = item[position].price

        holder.orderfinalPrice.text = item[position].sellingPrice

        coast = item[position].price?.replace("".toRegex(), "")!!.toDouble()
        finalCoast = item[position].sellingPrice?.replace("".toRegex(), "")!!.toDouble()
        quantity = selectedProductIDsList.get(item[position]) ?: 1
        holder.orderQuantity.text = quantity.toString()
        holder.orderIncrement.setOnClickListener {
            finalCoast = finalCoast + coast
            quantity++
            holder.orderfinalPrice.text = "" + finalCoast
            holder.orderQuantity.text = "" + quantity

        }

        holder.orderDecrement.setOnClickListener {
            if (quantity > 1) {
                finalCoast = finalCoast - coast
                quantity--
                holder.orderfinalPrice.text = "" + finalCoast
                holder.orderQuantity.text = "" + quantity
            }
        }
        holder.orderRemove.setOnClickListener {


            remove(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, itemCount)

            val tx: Double = (context as MyCartSpActivity).subtotal.text.toString().trim().replace("", "").toDouble()
            val totalPrice: Double = tx - coast.toString().replace("", "").toDouble()
            val deliveryFee: Double = (context).deliveryFees.toString().replace("", "").toDouble()
            val taxFee: Double = (context).taxFees.toString().replace("", "").toDouble()
            val stotalprice = String.format("%.2f", totalPrice).toDouble() - String.format("%.2f", deliveryFee).toDouble()

        }
        holder.orderSubTotal.text = "" + finalCoast
        holder.taxAndFees.text = ""
        holder.discount.text = ""

    }


    fun remove(index: Int) {
        if (index < 0 || index >= item.size) {
            Toast.makeText(context, "remove at :" + item, Toast.LENGTH_LONG).show()
        }
        selectedProductIDsList.remove(item[index].productId)
        item.removeAt(index)

    }

    override fun getItemCount(): Int {
        return item.size
    }

    inner class OrderViewHolder(item: View) : RecyclerView.ViewHolder(item) {

        var orderRemove: TextView = item.findViewById(R.id.removeCart)
        var orderProductName: TextView = item.findViewById(R.id.product_name)
        var taxAndFees: TextView = item.findViewById(R.id.taxFees)
        var discount: TextView = item.findViewById(R.id.deliveryFees)
        var orderSubTotal: TextView = item.findViewById(R.id.pricetv)
        var orderDecrement: TextView = item.findViewById(R.id.decrement)
        var orderQuantity: TextView = item.findViewById(R.id.quantity)
        var orderIncrement: TextView = item.findViewById(R.id.incremnet)
        var orderPrice: TextView = item.findViewById(R.id.originalPrice)
        var orderfinalPrice: TextView = item.findViewById(R.id.finalPrice)

    }

}


