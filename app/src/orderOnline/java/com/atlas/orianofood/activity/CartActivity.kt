package com.atlas.orianofood.activity

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.atlas.orianofood.R
import com.atlas.orianofood.adapter.CartAdapter
import com.atlas.orianofood.database.DatabaseHandler
import com.atlas.orianofood.model.Cart
import com.atlas.orianofood.model.Order
import com.atlas.orianofood.model.Request
import com.atlas.orianofood.utils.toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.orderOnline.activity_cart.*
import java.text.DateFormat
import java.text.NumberFormat
import java.util.*


class CartActivity : AppCompatActivity() {

    lateinit var database: FirebaseDatabase
    lateinit var requestRef: DatabaseReference
    lateinit var adapter: CartAdapter
    lateinit var carts: List<Cart>
    var total = 0
    val locale = Locale("en", "IN")
    val nf = NumberFormat.getCurrencyInstance(locale)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        //firebase init
        database = FirebaseDatabase.getInstance()
        requestRef = database.getReference("Request")

        loadListProduct()
    }

    @SuppressLint("SetTextI18n")
    private fun loadListProduct() {


        DatabaseHandler(this).createOrderTable()
        carts = DatabaseHandler(this).getCarts()
        //carts = Database(this).getCarts()
        adapter = CartAdapter(this, carts)
        val manager = LinearLayoutManager(this)
        recyclerview_cart.layoutManager = manager
        recyclerview_cart.setHasFixedSize(true)
        recyclerview_cart.adapter = adapter

        //total of price
        for (order in carts) {
            total += (Integer.parseInt(order.price)) * (Integer.parseInt(order.quantity))
        }

        total_cart_price.text = nf.format(total.toDouble())

        btn_buy.setOnClickListener {
            if (carts.isEmpty())
                "Please, add product to your cart.".toast(this)
            else
                showDialog()
        }
    }

    private fun showDialog() {
        val dialog = Dialog(this)
        dialog.setTitle("Checkout")
        dialog.setContentView(R.layout.dialog_request)
        dialog.findViewById<TextView>(R.id.confirm_cart_price).text = nf.format(total.toDouble())

        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) {
            when {
                currentUser.displayName?.isNotEmpty()!! -> {
                    dialog.findViewById<TextView>(R.id.txt_confirm_order_name).text =
                        currentUser.displayName
                    dialog.findViewById<TextView>(R.id.txt_confirm_order_phone).text =
                        currentUser.phoneNumber
                }
                !currentUser.phoneNumber.isNullOrEmpty() -> {
                    dialog.findViewById<TextView>(R.id.txt_confirm_order_name).text =
                        currentUser.phoneNumber
                    dialog.findViewById<TextView>(R.id.txt_confirm_order_phone).text =
                        currentUser.phoneNumber
                }
                !currentUser.email.isNullOrEmpty() -> {
                    dialog.findViewById<TextView>(R.id.txt_confirm_order_name).text =
                        currentUser.email
                    dialog.findViewById<TextView>(R.id.txt_confirm_order_phone).text =
                        currentUser.phoneNumber
                }
            }
        }


        var addr = dialog.findViewById<TextView>(R.id.txt_confirm_order_address)
        addr.text = DatabaseHandler(this).getDefaultAddress()



        dialog.findViewById<Button>(R.id.btn_cancel).setOnClickListener {
            dialog.cancel()
        }
        dialog.findViewById<Button>(R.id.btn_confirm).setOnClickListener {
            val request = Request(
                dialog.findViewById<TextView>(R.id.txt_confirm_order_phone).text.toString(),
                dialog.findViewById<TextView>(R.id.txt_confirm_order_name).text.toString(),
                addr.text.toString(),
                total_cart_price.text.toString(),
                carts
            )

            val date = Date()

            val order = Order(
                "OF${System.currentTimeMillis()}",
                DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.SHORT).format(date),
                addr.text.toString(),
                total_cart_price.text.toString(), "Home Delivery",
                DatabaseHandler(this).getOrderedItemsFromCart(),
                "Order Processing",
                "Online",
                dialog.findViewById<TextView>(R.id.txt_confirm_order_phone).text.toString(),
                dialog.findViewById<TextView>(R.id.txt_confirm_order_name).text.toString()
            )

            //submit to firebase
            /*val requestKey = System.currentTimeMillis()
            requestRef.child(requestKey.toString()).setValue(request)*/

            DatabaseHandler(this).createOrderDetailsTable()
            DatabaseHandler(this).addToOrderDetails(order)
            DatabaseHandler(this).createOrderTable()
            DatabaseHandler(this).cleanCart()
            toast("Your order placed successfully")

            //delete cart
            //Database(this).cleanCart()

            val intent = Intent(this@CartActivity, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }

        dialog.show()
    }


    fun Any.toast(context: Context, duration: Int = Toast.LENGTH_SHORT): Toast {
        return Toast.makeText(context, this.toString(), duration).apply { show() }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this@CartActivity, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}
