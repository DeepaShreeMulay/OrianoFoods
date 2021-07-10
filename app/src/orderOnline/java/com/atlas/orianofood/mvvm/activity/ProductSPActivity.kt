package com.atlas.orianofood.mvvm.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.atlas.orianofood.R
import com.atlas.orianofood.firebaseRT.activity.CartActivity
import com.atlas.orianofood.firebaseRT.activity.MyAddressesActivity
import com.atlas.orianofood.firebaseRT.activity.OrdersActivity
import com.atlas.orianofood.firebaseRT.activity.ProfileActivity
import com.atlas.orianofood.firebaseRT.utils.selectedProductIDsList
import com.atlas.orianofood.mvvm.product.adapter.ProductAdapter
import com.atlas.orianofood.mvvm.product.model.ProductItems
import com.atlas.orianofood.mvvm.product.model.ProductViewModel
import com.atlas.orianofood.mvvm.reciver.StateChangeReciver
import com.atlas.orianofood.mvvm.utils.logout
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_my_cart.*
import kotlinx.android.synthetic.orderOnline.activity_product.*
import kotlinx.android.synthetic.orderOnline.app_bar_product.*
import kotlinx.android.synthetic.orderOnline.app_bar_product.toolbar
import kotlinx.android.synthetic.orderOnline.content_product.*
import kotlinx.android.synthetic.orderOnline.nav_header_home.*

class ProductSPActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
    StateChangeReciver.StateChangeListener {
    private val cviewModel: ProductViewModel by lazy {
        ViewModelProvider(this).get(ProductViewModel::class.java)
    }

    private lateinit var productAdapter: ProductAdapter
    private val activity = this@ProductSPActivity
    private var selectedCategoryId: String? = null
    private lateinit var productItems: MutableList<ProductItems>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)

        selectedCategoryId = intent.getStringExtra("CategoryID").toString()

        fab.setOnClickListener { view ->

            /* Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                     .setAction("Action", null).show()*/
            if (selectedProductIDsList.isEmpty()) {
                Toast.makeText(this, "Please Add items in the cart", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(activity, MyCartSpActivity::class.java)
                startActivity(intent)

            }

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

        val manager = GridLayoutManager(activity, 2)
        productRecyclerView.layoutManager = manager
        productRecyclerView.setHasFixedSize(true)
        loadProductItems()
    }

    private fun loadProductItems() {
        productAdapter = ProductAdapter(this, mutableListOf())
        productRecyclerView.adapter = productAdapter

        var catagoryList = ArrayList<ProductItems>()

        with(cviewModel) {
            productData.observe(activity, Observer {
                if (it!!.plist.isNotEmpty()) {
                    catagoryList = appDatabase.productDao.selectDataByCategoryId(selectedCategoryId!!) as ArrayList<ProductItems>
                    productAdapter.clear()
                    Log.e("ProductSPActivity", "Category List : " + it.plist)
                    productAdapter.add(catagoryList)

                } else {
                    Log.e("error", "error in connect with adapter")
                }
            })


            pshowToast.observe(activity, Observer {
                Toast.makeText(activity, "$it", Toast.LENGTH_SHORT).show()
            })
            error.observe(activity, Observer {

                Toast.makeText(activity, "$it", Toast.LENGTH_SHORT).show()
            })
        }


    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
            val intent = Intent(this, HomeSPActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.nav_home -> {
                // Handle the camera action
                val intent = Intent(activity, HomeSPActivity::class.java)
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
            R.id.nav_privacy_policy -> {
                val intent = Intent(activity, PrivacyPolicyActivity::class.java)
                startActivity(intent)
                finish()
            }
            R.id.nav_terms -> {
                val intent = Intent(activity, TermsAndCondition::class.java)
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

    override fun onStateChanged(sessionStates: String?) {
        /*    for (i in 0 until productItems.size) {
                if (changedQuantity.containsKey(productItems[i])) {
                    productAdapter.updateElegentButton(i)
                }
            }*/
        productAdapter.notifyDataSetChanged()
    }

}