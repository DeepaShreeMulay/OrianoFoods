package com.atlas.orianofood.mvvm.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.atlas.orianofood.R
import com.atlas.orianofood.firebaseRT.utils.selectedProductIDsList
import com.atlas.orianofood.mvvm.order.OrderAdapter
import kotlinx.android.synthetic.main.activity_my_cart.*

class MyCartSpActivity : AppCompatActivity() {


    private lateinit var orderAdapter: OrderAdapter
    val productIds = HashMap<Int, Int>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_cart)

        // val orderDao=AppDatabase.getInstance(application)?.orderDao!!

        val orderManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        itemRecyclerview.layoutManager = orderManager
        // var items=orderDao.getAllOrder()


        //productIds.putAll(selectedProductIDsList)


        Log.e("MyCartSpActivity", "List from Product ids : " + productIds[0])
        orderAdapter = OrderAdapter(this, selectedProductIDsList)



        itemRecyclerview.adapter = orderAdapter


    }


}


