package com.atlas.orianofood.mvvm.order

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.atlas.orianofood.R
import com.atlas.orianofood.core.App
import com.atlas.orianofood.firebaseRT.utils.Common.sendStateChangedBroadCast
import com.atlas.orianofood.firebaseRT.utils.UpdateItemToProductIdMap
import com.atlas.orianofood.firebaseRT.utils.selectedProductIDsList
import com.atlas.orianofood.mvvm.activity.EmptyCart
import com.atlas.orianofood.mvvm.activity.MyCartSpActivity
import com.atlas.orianofood.mvvm.database.AppDatabase
import com.atlas.orianofood.mvvm.product.model.ProductItems
import java.util.*
import kotlin.properties.Delegates

class OrderAdapter(val context: Context, orderList: HashMap<Int, Int>) : RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {
   //val actions:Actions=context as Actions
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
        //  sendStateChangedBroadCast(context, "UPDATED")
        updatePriceAtView()

    }

    private fun updateQuantityAndPrice() {
        item.forEach {
            it.quantity = selectedProductIDsList[it.productId]!!
            totalPrice += (it.price)?.toDouble()!! * it.quantity
        }

    }


    fun updatePrice() {
        totalPrice = 0.0
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
            holder.orderfinalPrice.text =
                (item[position].price?.toDouble()!! * item[position].quantity).toString()
            holder.orderQuantity.text = item[position].quantity.toString()
            UpdateItemToProductIdMap(context, item[position].productId, true)
            // changedQuantity.put(item[position].productId,item[position].quantity)
            //  sendStateChangedBroadCast(context,"UPDATED")

        }

        holder.orderDecrement.setOnClickListener {
            if (item[position].quantity > 1) {
                finalCoast -= coast
                item[position].quantity -= 1
                totalPrice -= item[position].price?.toDouble()!!
                updatePriceAtView()
                holder.orderfinalPrice.text =
                    (item[position].price?.toDouble()!! * item[position].quantity).toString()
                holder.orderQuantity.text = item[position].quantity.toString()
                UpdateItemToProductIdMap(context, item[position].productId, false)
                //changedQuantity.put(item[position].productId,item[position].quantity)
                //sendStateChangedBroadCast(context,"UPDATED")
            }
        }
        holder.orderRemove.setOnClickListener {

//           selectedProductIDsList.remove(item[position].productId)


//            actions!!.hideButton(selectedProductIDsList.remove(item[position].productId)!!)

            //changedQuantity.put(item[position].productId,item[position].quantity)

//            item.forEach {
//                totalPrice -= (it.price)?.toDouble()!! * it.quantity
//            }
            remove(position)

            sendStateChangedBroadCast(context, "UPDATED")
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, itemCount)

            if (selectedProductIDsList.isEmpty()) {
                val intent = Intent(context, EmptyCart::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }
                context.startActivity(intent)
                Toast.makeText(context, "please add items in cart", Toast.LENGTH_LONG).show()

            }

            updatePrice()
            updatePriceAtView()


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


