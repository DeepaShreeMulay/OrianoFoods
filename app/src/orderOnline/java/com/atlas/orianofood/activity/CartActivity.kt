package com.atlas.orianofood.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import kotlinx.android.synthetic.orderOnline.activity_cart.*
import org.json.JSONObject
import java.text.DateFormat
import java.text.NumberFormat
import java.util.*


class CartActivity : AppCompatActivity(), PaymentResultListener {

    lateinit var database: FirebaseDatabase
    lateinit var requestRef: DatabaseReference
    lateinit var adapter: CartAdapter
    lateinit var order: Order
    lateinit var carts: List<Cart>
    val TAG: String = CartActivity::class.toString()
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
        val dialog = Dialog(this, R.style.MyAlertDialogTheme)
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
                    dialog.findViewById<TextView>(R.id.txt_confirm_order_email).text =
                        currentUser.email
                }
                !currentUser.phoneNumber.isNullOrEmpty() -> {
                    dialog.findViewById<TextView>(R.id.txt_confirm_order_name).text =
                        currentUser.phoneNumber
                    dialog.findViewById<TextView>(R.id.txt_confirm_order_phone).text =
                        currentUser.phoneNumber
                    dialog.findViewById<TextView>(R.id.txt_confirm_order_email).text =
                        currentUser.email
                }
                !currentUser.email.isNullOrEmpty() -> {
                    dialog.findViewById<TextView>(R.id.txt_confirm_order_name).text =
                        currentUser.email
                    dialog.findViewById<TextView>(R.id.txt_confirm_order_phone).text =
                        currentUser.phoneNumber
                    dialog.findViewById<TextView>(R.id.txt_confirm_order_email).text =
                        currentUser.email
                }
            }
        }


        var addr = dialog.findViewById<TextView>(R.id.txt_confirm_order_address)
        addr.text = DatabaseHandler(this).getDefaultAddress()

        addr.setOnClickListener {
            toast("Work in Progress. Soon you will see this feature.")
        }



        dialog.findViewById<Button>(R.id.btn_cancel).setOnClickListener {
            dialog.cancel()
        }
        dialog.findViewById<Button>(R.id.btn_confirm).setOnClickListener {
            startPayment(
                email = dialog.findViewById<TextView>(R.id.txt_confirm_order_email).text.toString(),
                phone = dialog.findViewById<TextView>(R.id.txt_confirm_order_phone).text.toString(),
                price = (total * 100).toInt()
            )

            val request = Request(
                if (dialog.findViewById<TextView>(R.id.txt_confirm_order_phone).text.isNotEmpty()) {
                    dialog.findViewById<TextView>(R.id.txt_confirm_order_phone).text.toString()
                } else {
                    dialog.findViewById<TextView>(R.id.txt_confirm_order_email).text.toString()
                },
                dialog.findViewById<TextView>(R.id.txt_confirm_order_name).text.toString(),
                addr.text.toString(),
                total_cart_price.text.toString(),
                carts
            )

            val date = Date()

            order = Order(
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


        }

        dialog.show()
    }

    private fun startPayment(email: String, phone: String, price: Int) {
        /*
        *  You need to pass current activity in order to let Razorpay create CheckoutActivity
        * */
        val activity: Activity = this
        val co = Checkout()

        try {
            val options = JSONObject()
            options.put("name", "Oriano Restaurant")
            options.put("description", "Demoing Charges")
            //You can omit the image option to fetch the image from dashboard
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png")
            options.put("currency", "INR")
            options.put("amount", price)
            options.put("send_sms_hash", true)

            val prefill = JSONObject()
            prefill.put("email", if (email.isNotEmpty()) email else "test@razorpay.com")
            prefill.put("contact", if (phone.isNotEmpty()) phone else "9021066696")

            options.put("prefill", prefill)
            co.open(activity, options)
        } catch (e: Exception) {
            Toast.makeText(activity, "Error in payment: " + e.message, Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }

    override fun onPaymentError(errorCode: Int, response: String?) {
        try {
            Toast.makeText(this, "Payment failed $errorCode \n $response", Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            Log.e(TAG, "Exception in onPaymentSuccess", e)
        }
    }

    override fun onPaymentSuccess(razorpayPaymentId: String?) {
        try {
            Toast.makeText(this, "Payment Successful $razorpayPaymentId", Toast.LENGTH_LONG).show()
            order.orderId = razorpayPaymentId ?: "Issue in PaymentID"

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
        } catch (e: Exception) {
            Log.e(TAG, "Exception in onPaymentSuccess", e)
        }
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
