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
import com.atlas.orianofood.firebaseRT.utils.UpdateItemToProductIdMap
import com.atlas.orianofood.firebaseRT.utils.selectedProductIDsList
import com.atlas.orianofood.mvvm.activity.MyCartSpActivity
import com.atlas.orianofood.mvvm.database.AppDatabase
import com.atlas.orianofood.mvvm.product.model.ProductItems
import java.util.*
import kotlin.properties.Delegates

class OrderAdapter(val context: Context, orderList: HashMap<Int, Int>) : RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {


    private var quantity = 0
    var coast = 0.0
    private var finalCoast = 0.0
    private val productdao = AppDatabase.getInstance(App.appContext)?.productDao!!

    private lateinit var item: ArrayList<ProductItems>

    private var totalPrice by Delegates.notNull<Double>()
    val deliveryFee: Double = (context as MyCartSpActivity).deliveryFees.toString().replace("", "").toDouble()

    init {
        totalPrice = 0.00

        item = productdao.selectDataByProductIdList(orderList.keys.toList()) as ArrayList<ProductItems>
        updateQuantityAndPrice()
        updatePriceAtView()

    }//btn change nhi hue add krne p elegent btn nhi aara product me se add karo home pe code nhi he

    private fun updateQuantityAndPrice() {
        item.forEach {
            it.quantity = selectedProductIDsList[it.productId]!!
            totalPrice += (it.price)?.toDouble()!! * it.quantity
        }
    }
    //haan aaya pr elegent p click krne p product cart me quantity me change nhi aaya wo code karna he


    fun updatePrice() {
        item.forEach {
            totalPrice += (it.price)?.toDouble()!! * it.quantity
        }
    }

    fun updatePriceAtView() {
        (context as MyCartSpActivity).stotalTv.text = String.format("%.2f", totalPrice)
        context.allTotalPriceTv.text = String.format("%.2f", String.format("%.2f", totalPrice + deliveryFee).toDouble())
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        var orderview = LayoutInflater.from(parent.context).inflate(R.layout.my_cart_item, parent, false)
        return OrderViewHolder(orderview)
    }


    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {

        holder.orderProductName.text = item[position].productName
        holder.orderPrice.text = item[position].price

        holder.orderfinalPrice.text = (item[position].price?.toDouble()!! * item[position].quantity).toString()

        coast = item[position].price?.replace("".toRegex(), "")!!.toDouble()
        finalCoast = item[position].sellingPrice?.replace("".toRegex(), "")!!.toDouble()
        quantity = item[position].quantity
        holder.orderQuantity.text = item[position].quantity.toString()
        holder.orderIncrement.setOnClickListener {
            finalCoast += coast
            item[position].quantity += 1
            totalPrice += item[position].price?.toDouble()!!
            updatePriceAtView()
            holder.orderfinalPrice.text = finalCoast.toString()
            holder.orderQuantity.text = item[position].quantity.toString()
            UpdateItemToProductIdMap(item[position].productId, true)
        }

        holder.orderDecrement.setOnClickListener {
            if (quantity > 1) {
                finalCoast -= coast
                item[position].quantity -= 1
                totalPrice -= item[position].price?.toDouble()!!
                updatePriceAtView()
                holder.orderfinalPrice.text = "" + finalCoast
                holder.orderQuantity.text = "" + quantity
                UpdateItemToProductIdMap(item[position].productId, false)
            }
        }
        holder.orderRemove.setOnClickListener {

            selectedProductIDsList.remove(item[position].productId)
            remove(position)
            notifyItemRemoved(position)
            updatePrice()
            notifyItemRangeChanged(position, itemCount)

            /*     val tx: Double = (context as MyCartSpActivity).allTotalPriceTv?.text.toString().trim().replace("", "").toDouble()
                 val totalPrice: Double = tx - coast.toString().replace("", "").toDouble()
                 val deliveryFee: Double = (context as MyCartSpActivity).deliveryFees.toString().replace("", "").toDouble()
                // val taxFee: Double = (context as MyCartSpActivity).taxFees.toString().replace("", "").toDouble()
                 val stotalprice = String.format("%.2f", totalPrice).toDouble() - String.format("%.2f", deliveryFee).toDouble()

                 (context as MyCartSpActivity).allTotalPrice = 0.00
                 (context as MyCartSpActivity).stotalTv?.setText("" + String.format("%.2f", stotalprice))
                 (context as MyCartSpActivity).allTotalPriceTv?.setText("" + String.format("%.2f", String.format("%.2f", totalPrice).toDouble()))
     */


        }


    }


    fun remove(index: Int) {
        if (index < 0 || index >= item.size) {
            Toast.makeText(context, "remove at :" + item, Toast.LENGTH_LONG).show()
        }
        selectedProductIDsList.remove(item[index].productId)
        item.removeAt(index)

    }


    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount(): Int {
        return item.size
    }

    fun getItems(): ArrayList<ProductItems> {
        return item
    }

    inner class OrderViewHolder(item: View) : RecyclerView.ViewHolder(item) {

        var orderRemove: TextView = item.findViewById(R.id.removeCart)
        var orderProductName: TextView = item.findViewById(R.id.product_name)

        // var taxAndFees: TextView = item.findViewById(R.id.taxFees)
        // var discount: TextView = item.findViewById(R.id.deliveryFees)
        // var orderSubTotal: TextView = item.findViewById(R.id.pricetv)
        var orderDecrement: TextView = item.findViewById(R.id.decrement)
        var orderQuantity: TextView = item.findViewById(R.id.quantity)
        var orderIncrement: TextView = item.findViewById(R.id.incremnet)
        var orderPrice: TextView = item.findViewById(R.id.originalPrice)
        var orderfinalPrice: TextView = item.findViewById(R.id.finalPrice)
        // var delivery:TextView=item.findViewById(R.id.deliveryFees)

    }

}


