package com.atlas.orianofood.firebaseRT.activity

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.atlas.orianofood.R
import com.atlas.orianofood.firebaseRT.adapter.OrdersAdapter
import com.atlas.orianofood.firebaseRT.database.DatabaseHandler
import com.atlas.orianofood.firebaseRT.model.Order
import com.atlas.orianofood.firebaseRT.utils.logout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.orderOnline.activity_cart.toolbar
import kotlinx.android.synthetic.orderOnline.activity_home.*
import kotlinx.android.synthetic.orderOnline.app_bar_addresses.*
import kotlinx.android.synthetic.orderOnline.content_addresses.*
import kotlinx.android.synthetic.orderOnline.nav_header_home.view.*


class OrdersActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var adapter: OrdersAdapter
    lateinit var orderlist: List<Order>

    private val currentUser = FirebaseAuth.getInstance().currentUser
    private val activity = this@OrdersActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addresses)
        toolbar.title = ""
        setSupportActionBar(toolbar)

        fab.setOnClickListener {
            val intent = Intent(activity, HomeActivity::class.java)
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
        val view: View = nav_view.getHeaderView(0)
        if (currentUser != null) {
            when {
                currentUser.displayName?.isNotEmpty()!! -> {
                    view.userLogged.text = currentUser.displayName
                }
                !currentUser.phoneNumber.isNullOrEmpty() -> {
                    view.userLogged.text = currentUser.phoneNumber
                }
                !currentUser.email.isNullOrEmpty() -> {
                    view.userLogged.text = currentUser.email
                }
            }
        }

        loadOrders()
    }

    private fun loadOrders() {
        DatabaseHandler(this).createOrderDetailsTable()
        orderlist = DatabaseHandler(this).getOrderDetails()
        if (orderlist.isEmpty()) {
            tvNoAddress.visibility = View.VISIBLE
            tvNoAddress.text = "No Order Placed"
            recyclerview.visibility = View.INVISIBLE
        } else {
            tvNoAddress.visibility = View.INVISIBLE
            recyclerview.visibility = View.VISIBLE
            adapter = OrdersAdapter(this, orderlist)
            val manager = LinearLayoutManager(this)
            recyclerview.layoutManager = manager
            recyclerview.setHasFixedSize(true)
            recyclerview.adapter = adapter
        }
    }


    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
            val intent = Intent(this@OrdersActivity, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_home -> {
                // Handle the camera action
                val intent = Intent(activity, HomeActivity::class.java)
                startActivity(intent)
                finish()
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
                val intent = Intent(activity, GalleryActivity::class.java)
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