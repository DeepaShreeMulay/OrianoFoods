package com.atlas.orianofood.mvvm.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.atlas.orianofood.R
import com.atlas.orianofood.mvvm.category.adapter.CategoryAdapter
import com.atlas.orianofood.mvvm.category.model.CategoryViewModel
import kotlinx.android.synthetic.orderOnline.activity_category_s_p.*
import kotlinx.android.synthetic.orderOnline.app_bar_home.*
import kotlinx.android.synthetic.orderOnline.content_home.*

class CategorySPActivity : AppCompatActivity() {
    private val cviewModel: CategoryViewModel by lazy{ ViewModelProvider(this).get(CategoryViewModel::class.java)}
    private lateinit var categoryadapter: CategoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_s_p)

        toolbar.title = ""
        setSupportActionBar(toolbar)

        val manager = GridLayoutManager(this@CategorySPActivity, 3)
        recyclerview.layoutManager = manager
        recyclerview.setHasFixedSize(true)
        categoryadapter= CategoryAdapter(mutableListOf())
        categoryRecyclerView.adapter = categoryadapter

        with(cviewModel) {
            homeData.observe(this@CategorySPActivity, Observer {
                Toast.makeText(this@CategorySPActivity,"homedata  run",Toast.LENGTH_SHORT).show()
                if (it!!.list.isNotEmpty()) {

                    categoryadapter.clear()
                    categoryadapter.add(it.list)

                }else{
                    Log.e("error","error in connect with adapter")
                }
            })


            showToast.observe(this@CategorySPActivity, Observer {
                Toast.makeText(this@CategorySPActivity,"$it",Toast.LENGTH_SHORT).show()
            })
            error.observe(this@CategorySPActivity, Observer {

                Toast.makeText(this@CategorySPActivity,"$it",Toast.LENGTH_SHORT).show()
            })
        }


    }
}