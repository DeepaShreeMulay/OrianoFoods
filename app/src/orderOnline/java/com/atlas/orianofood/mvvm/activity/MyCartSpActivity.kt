package com.atlas.orianofood.mvvm.activity

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.atlas.orianofood.R
import com.atlas.orianofood.firebaseRT.utils.selectedProductIDsList
import com.atlas.orianofood.mvvm.order.OrderAdapter
import kotlinx.android.synthetic.main.activity_my_cart.*

class MyCartSpActivity : AppCompatActivity() {


    private lateinit var orderAdapter: OrderAdapter
    val productIds = HashMap<Int, Int>()
    var allTotalPrice = 0.0
    var stotalTv: TextView? = null
    var dFeetv: TextView? = null
    var allTotalPriceTv: TextView? = null
    var deliveryFees = 50


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_cart)
        dFeetv = findViewById(R.id.deliveryFees)
        stotalTv = findViewById(R.id.pricetv)
        allTotalPriceTv = findViewById(R.id.allTotal)

        // val orderDao=AppDatabase.getInstance(application)?.orderDao!!

        val orderManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        itemRecyclerview.layoutManager = orderManager

        Log.e("MyCartSpActivity", "List from Product ids : " + productIds[0])
        orderAdapter = OrderAdapter(this, selectedProductIDsList)


        allTotalPrice += 120

        itemRecyclerview.adapter = orderAdapter
        dFeetv?.text = "" + deliveryFees

    }


}


