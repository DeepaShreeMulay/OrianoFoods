package com.atlas.orianofood.mvvm.activity

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.atlas.orianofood.R
import com.atlas.orianofood.core.App
import com.atlas.orianofood.firebaseRT.activity.MyAddressesActivity
import com.atlas.orianofood.firebaseRT.database.DatabaseHandler
import com.atlas.orianofood.firebaseRT.model.Order
import com.atlas.orianofood.firebaseRT.model.RequestHashMap
import com.atlas.orianofood.firebaseRT.utils.selectedProductIDsList
import com.atlas.orianofood.mvvm.database.AppDatabase
import com.atlas.orianofood.mvvm.order.OrderAdapter
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import kotlinx.android.synthetic.main.activity_my_cart.*
import kotlinx.android.synthetic.orderOnline.dialog_request.*
import kotlinx.android.synthetic.orderOnline.nav_header_home.*
import org.json.JSONObject
import java.text.DateFormat
import java.util.*
import kotlin.collections.HashMap


class MyCartSpActivity : AppCompatActivity(), PaymentResultListener {


    private lateinit var orderAdapter: OrderAdapter
    val productIds = HashMap<Int, Int>()
    var allTotalPrice = 0.0
    lateinit var order: Order
    lateinit var stotalTv: TextView
    private lateinit var dFeetv: TextView
    lateinit var allTotalPriceTv: TextView
    private lateinit var btnConfirm: Button
    var deliveryFees = 50
    lateinit var address: String
    val profileDao = AppDatabase.getInstance(App.appContext)?.profileDao!!
    val TAG: String = MyCartSpActivity::class.toString()
    private lateinit var confirm_order_email: TextView
    private lateinit var confirm_order_phone: TextView
    private lateinit var confirm_order_name: TextView
    private lateinit var confirm_order_address: TextView

    /* init {


         profileDao.selectAllData().forEach {
             Log.e("CART","${it.userEmail}")
             confirm_order_email?.setText("${it.userLogin}")
             confirm_order_phone?.setText("${it.userEmail}")
             confirm_order_name?.setText("${it.displayName}")
         }

     }*/


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_cart)
        dFeetv = findViewById(R.id.deliveryFees)
        stotalTv = findViewById(R.id.pricetv)
        allTotalPriceTv = findViewById(R.id.allTotal)
        btnConfirm = findViewById(R.id.btnconfirm)
        confirm_order_email = findViewById(R.id.login_email)
        confirm_order_phone = findViewById(R.id.login_mobile)
        confirm_order_name = findViewById(R.id.cartName)
        confirm_order_address = findViewById(R.id.selectAddress)

        // val orderDao=AppDatabase.getInstance(application)?.orderDao!!
        profileDao.selectAllData().forEach {
            Log.e("CART", it.userEmail)
            confirm_order_email.text = it.userLogin
            confirm_order_phone.text = it.userEmail
            confirm_order_name.text = it.displayName
        }

        val orderManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        itemRecyclerview.layoutManager = orderManager

        Log.e("MyCartSpActivity", "List from Product ids : " + productIds[0])
        orderAdapter = OrderAdapter(this, selectedProductIDsList)


        allTotalPrice += 120

        itemRecyclerview.adapter = orderAdapter
        dFeetv.text = deliveryFees.toString()

        selectAddress.setOnClickListener {

            val intent = Intent(this, MyAddressesActivity::class.java)

            startActivityForResult(intent, 2)
        }

        /*var name = intent.getStringExtra("UserName")

        cartName.text = name
       var mobile = intent.getStringExtra("UserMobile")
        if (mobile?.isNotEmpty()!!) {
            login_mobile.text = mobile


        } else {
            add_mobile.setOnClickListener {
                val view: View = LayoutInflater.from(this).inflate(R.layout.add_mobile_dialog, null)
                val builder = AlertDialog.Builder(this)
                builder.setView(view)
                //code inside it
                val dialog = builder.create()
                dialog.show()
            }
        }*/

        profileDao.selectAllData().forEach {
            cartName.text = it.displayName
            if (login_mobile.text.isNotEmpty()) {
                login_mobile.text = it.userEmail
                add_mobile.setVisible(false)

            } else {
                add_mobile.setOnClickListener {
                    val view: View =
                        LayoutInflater.from(this).inflate(R.layout.add_mobile_dialog, null)
                    val builder = AlertDialog.Builder(this)
                    builder.setView(view)
                    //code inside it
                    val dialog = builder.create()
                    dialog.show()
                }
            }
        }



        btnConfirm.setOnClickListener {

            startPayment(

                // email = /*dialog.findViewById<TextView>(R.id.txt_confirm_order_email).text.toString()*/"EMAIL",
                email = confirm_order_email.text.toString(),
                phone = confirm_order_phone.text.toString(),

                //  phone = /*dialog.findViewById<TextView>(R.id.txt_confirm_order_phone).text.toString()*/ "PHONE",
                price = (allTotalPrice * 100).toInt()

            )


            /* val request = RequestHashMap(
                     if (dialog.findViewById<TextView>(R.id.txt_confirm_order_phone).text.isNotEmpty()) {
                     dialog.findViewById<TextView>(R.id.txt_confirm_order_phone).text.toString()
                 } else {
                     dialog.findViewById<TextView>(R.id.txt_confirm_order_email).text.toString()
                 }"PHONE",
                      dialog.findViewById<TextView>(R.id.txt_confirm_order_name).text.toString()"NAME",
                    // txt_confirm_order_name.text.toString()?:"NAME",
                     addr.text.toString()"ADDRESS",
                     allTotalPrice.toString(),
                     selectedProductIDsList
             )*/
            val request = RequestHashMap(
                    if (confirm_order_phone.text.isNotEmpty()) {
                        // dialog.findViewById<TextView>(R.id.txt_confirm_order_phone).text.toString()
                        confirm_order_phone.text.toString()
                    } else {
                        // dialog.findViewById<TextView>(R.id.txt_confirm_order_email).text.toString()
                        confirm_order_email.text.toString()
                    },
                    /* dialog.findViewById<TextView>(R.id.txt_confirm_order_name).text.toString()*/
                    confirm_order_name.text.toString(),

                    // txt_confirm_order_name.text.toString()?:"NAME",
                    confirm_order_address.text.toString(),
                    allTotalPrice.toString(),
                    selectedProductIDsList
            )

            val date = Date()

            order = Order(
                "OF${System.currentTimeMillis()}",
                DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.SHORT).format(date),
                // /*addr.text.toString()*/"ADDRESS",
                confirm_order_address.text.toString(),
                allTotalPrice.toString(), "Home Delivery",
                DatabaseHandler(this).getOrderedItemsFromCart(),
                "Order Processing",
                "Online",
                ///*dialog.findViewById<TextView>(R.id.txt_confirm_order_phone).text.toString()*/"PHONE",
                confirm_order_phone.text.toString(),
                //  /*dialog.findViewById<TextView>(R.id.txt_confirm_order_name).text.toString()*/ "NAME"
                confirm_order_name.text.toString()

            )



            //submit to firebase
            /*val requestKey = System.currentTimeMillis()
            requestRef.child(requestKey.toString()).setValue(request)*/


        }


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
            prefill.put("email", email /*?: "test@razorpay.com"*/)
            prefill.put("contact", phone  /*"9021066696"*/)

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
            Toast.makeText(this, "Your order placed successfully", Toast.LENGTH_LONG).show()

            //delete cart
            //Database(this).cleanCart()

            val intent = Intent(this@MyCartSpActivity, HomeSPActivity::class.java)
            startActivity(intent)
            finish()
        } catch (e: Exception) {
            Log.e(TAG, "Exception in onPaymentSuccess", e)
        }
    }


    fun Any.toast(context: Context, duration: Int = Toast.LENGTH_SHORT): Toast {
        return Toast.makeText(context, this.toString(), duration).apply { show() }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode === 2) {
            address = data?.getStringExtra("Address")!!
            selectAddress.text = address
        }
    }

    fun View.setVisible(visible: Boolean) {// ye kisliye he aagr mobile se login nhi h to view gone
        visibility = if (visible) {
            Button.VISIBLE
        } else {
            Button.GONE
        }
    }

}


