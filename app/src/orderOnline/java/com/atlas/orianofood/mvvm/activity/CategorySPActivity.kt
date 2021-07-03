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
import com.atlas.orianofood.firebaseRT.activity.*
import com.atlas.orianofood.mvvm.category.ShowCategoryData
import com.atlas.orianofood.mvvm.category.adapter.CategoryAdapter
import com.atlas.orianofood.mvvm.category.model.CategoryViewModel
import com.atlas.orianofood.mvvm.utils.logout
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.orderOnline.activity_category.*
import kotlinx.android.synthetic.orderOnline.app_bar_category.*
import kotlinx.android.synthetic.orderOnline.content_category.*

class CategorySPActivity : AppCompatActivity(), ShowCategoryData, NavigationView.OnNavigationItemSelectedListener {
    private val cviewModel: CategoryViewModel by lazy {
        ViewModelProvider(this).get(
                CategoryViewModel::class.java
        )
    }
    private lateinit var categoryadapter: CategoryAdapter
    private val activity = this@CategorySPActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        toolbar.title = ""
        setSupportActionBar(toolbar)


        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
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

        val manager = GridLayoutManager(this@CategorySPActivity, 3)
        categoryRecyclerView.layoutManager = manager
        categoryRecyclerView.setHasFixedSize(true)
        loadCategoryItems()
    }

    private fun loadCategoryItems() {
        categoryadapter = CategoryAdapter(activity, mutableListOf())
        categoryRecyclerView.adapter = categoryadapter

        with(cviewModel) {
            homeData.observe(activity, Observer {

                if (it!!.list.isNotEmpty()) {

                    categoryadapter.clear()
                    categoryadapter.add(it.list)

                } else {
                    Log.e("error", "error in connect with adapter")
                }
            })


            showToast.observe(activity, Observer {
                Toast.makeText(this@CategorySPActivity, "$it", Toast.LENGTH_SHORT).show()
            })
            error.observe(activity, Observer {

                Toast.makeText(this@CategorySPActivity, "$it", Toast.LENGTH_SHORT).show()
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
            R.id.nav_plans -> {
                // Handle the camera action
                val intent = Intent(activity, SubscriptionActivity::class.java)
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

    override fun transferCategoryData(data: String) {
        val intent = Intent(this, ProductSPActivity::class.java)
        intent.putExtra("CategoryID", data)
        startActivity(intent)
    }

}