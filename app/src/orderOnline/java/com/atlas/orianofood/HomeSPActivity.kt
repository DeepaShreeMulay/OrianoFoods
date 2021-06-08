package com.atlas.orianofood

import androidx.lifecycle.Observer
import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.GravityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.atlas.orianofood.activity.*
import com.atlas.orianofood.adapter.MenuViewHolder
import com.atlas.orianofood.category.adapter.CategoryAdapter
import com.atlas.orianofood.category.model.CategoryViewModel


import com.atlas.orianofood.topRatedProduct.adapter.TopAdapter
import com.atlas.orianofood.topRatedProduct.model.TopRatedViewModel
import com.atlas.orianofood.topRatedSelling.adapter.SellingAdapter
import com.atlas.orianofood.topRatedSelling.model.SellingViewModel

import com.atlas.orianofood.utils.*

import com.google.android.material.navigation.NavigationView

import com.google.firebase.database.*

import kotlinx.android.synthetic.orderOnline.activity_home.*
import kotlinx.android.synthetic.orderOnline.app_bar_home.*
import kotlinx.android.synthetic.orderOnline.content_home.*
import kotlinx.android.synthetic.orderOnline.nav_header_home.view.*
import java.text.NumberFormat
import java.util.*

class HomeSPActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private val activity = this
    private val viewModel: SellingViewModel by lazy { ViewModelProvider(this).get(SellingViewModel::class.java) }
    private lateinit var sellingAdapter: SellingAdapter



    // private val currentUser = LoginDataBase.getInstance(this).loginDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        toolbar.title = ""
        setSupportActionBar(toolbar)




        fab.setOnClickListener {
            val intent = Intent(activity, CartActivity::class.java)
            startActivity(intent)
            finish()
        }

        val toggle = ActionBarDrawerToggle(
                this,
                drawer_layout,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        //user name
       /* val view: View = nav_view.getHeaderView(0)
        if (currentUser != null) {
            println(currentUser.displayName + "   " + currentUser.phoneNumber + "   " + currentUser.email)
            if (!currentUser.displayName.isNullOrEmpty()) {
                view.userLogged.text = currentUser.displayName
            } else if (!currentUser.phoneNumber.isNullOrEmpty()) {
                view.userLogged.text = currentUser.phoneNumber
            } else if (!currentUser.email.isNullOrEmpty()) {
                view.userLogged.text = currentUser.email
            }
        }*/

        val manager = GridLayoutManager(this, 3)
        recyclerview.layoutManager = manager
        recyclerview.setHasFixedSize(true)
        loadMenuItems()

        val offerManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        topSellingRecyclerview.layoutManager = offerManager
        topSellingRecyclerview.setHasFixedSize(true)
        loadTopSellingItems()

        val adsManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        adRecyclerView.layoutManager = adsManager
        adRecyclerView.setHasFixedSize(true)
        //loadOffers()

        val topRatingManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        topRatingRecyclerview.layoutManager = topRatingManager
        topRatingRecyclerview.setHasFixedSize(true)
        loadTopRatingItems()  // Ads chodo Gallary
    }

    private val cviewModel: CategoryViewModel by lazy{ViewModelProvider(this).get(CategoryViewModel::class.java)}
    private lateinit var categoryadapter: CategoryAdapter

    private fun loadMenuItems() {

        categoryadapter= CategoryAdapter(mutableListOf())
        recyclerview.adapter = categoryadapter

        with(cviewModel) {
            homeData.observe(this@HomeSPActivity, Observer {
                Toast.makeText(this@HomeSPActivity,"homedata  run",Toast.LENGTH_SHORT).show()
                if (it!!.list.isNotEmpty()) {

                    categoryadapter.clear()
                    categoryadapter.add(it.list)

                }else{
                    Log.e("error","error in connect with adapter")
                }
            })


            showToast.observe(this@HomeSPActivity, Observer {
                // Toast.makeText(applicationContext, "$it", Toast.LENGTH_LONG).show()
                Toast.makeText(this@HomeSPActivity,"$it",Toast.LENGTH_SHORT).show()
            })
            error.observe(this@HomeSPActivity, Observer {

                Toast.makeText(this@HomeSPActivity,"$it",Toast.LENGTH_SHORT).show()
            })
        }

    }

    private fun loadTopSellingItems() {
        sellingAdapter = SellingAdapter(mutableListOf())
        topSellingRecyclerview.adapter = sellingAdapter

        with(viewModel) {
            sellingData.observe(this@HomeSPActivity, Observer {
                Toast.makeText(this@HomeSPActivity, "Productdata  run", Toast.LENGTH_SHORT).show()
                if (it!!.slist.isNotEmpty()) {

                    sellingAdapter.clear()
                    sellingAdapter.add(it.slist)

                } else {
                    Log.e("error", "error in connect with adapter")
                }
            })
            tshowToast.observe(this@HomeSPActivity, Observer {

                Toast.makeText(this@HomeSPActivity, "$it", Toast.LENGTH_SHORT).show()
            })
            error.observe(this@HomeSPActivity, Observer {

                Toast.makeText(this@HomeSPActivity, "$it", Toast.LENGTH_SHORT).show()
            })

        }
    }
    private val tviewModel: TopRatedViewModel by lazy { ViewModelProvider(this).get(TopRatedViewModel::class.java) }
    private lateinit var topAdapter: TopAdapter
    private fun loadTopRatingItems() {
        topAdapter = TopAdapter(mutableListOf())
        topRatingRecyclerview.adapter = topAdapter
        with(tviewModel) {
            topRatedData.observe(this@HomeSPActivity, Observer {
                Toast.makeText(this@HomeSPActivity, "Productdata  run", Toast.LENGTH_SHORT).show()
                if (it!!.rlist.isNotEmpty()) {

                    topAdapter.clear()
                    topAdapter.add(it.rlist)

                } else {
                    Log.e("error", "error in connect with adapter")
                }
            })
            topshowToast.observe(this@HomeSPActivity, Observer {

                Toast.makeText(this@HomeSPActivity ,"$it", Toast.LENGTH_SHORT).show()
            })
            error.observe(this@HomeSPActivity, Observer {

                Toast.makeText(this@HomeSPActivity, "$it", Toast.LENGTH_SHORT).show()
            })

        }
    }



    private fun loadOffers() {

            }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> {
                // Handle the camera action
                /*val intent = Intent(activity, HomeActivity::class.java)
                startActivity(intent)
                finish()*/
            }
            R.id.nav_address -> {
                // Handle the camera action
                val intent = Intent(activity, MyAddressesActivity::class.java)
                startActivity(intent)
                finish()
            }
            R.id.nav_menu -> {
                // Handle the camera action
            }
            R.id.nav_cart -> {
                // Handle the camera action
                val intent = Intent(activity, CartActivity::class.java)
                startActivity(intent)
                finish()
            }
            R.id.nav_gallery -> {
                // Handle the camera action
                val intent = Intent(activity, GallerySPActivity::class.java)
                startActivity(intent)
                finish()
            }
            R.id.nav_profile -> {
                // Handle the camera action
                val intent = Intent(activity, ProfileActivity::class.java)
                startActivity(intent)
                finish()
            }
            R.id.nav_orders -> {
                // Handle the camera action
                val intent = Intent(activity, OrdersActivity::class.java)
                startActivity(intent)
                finish()
            }
            R.id.nav_log_out -> {
                // Handle the camera action
                logout()
            }

        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

}


