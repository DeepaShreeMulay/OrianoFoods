package com.atlas.orianofood.firebaseRT.activity

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.atlas.orianofood.R
import com.atlas.orianofood.firebaseRT.activity.*
import com.atlas.orianofood.firebaseRT.activity.ui.main.SectionsPagerAdapter
import com.atlas.orianofood.firebaseRT.adapter.SubscriptionAdapter
import com.atlas.orianofood.firebaseRT.model.Subscription
import com.atlas.orianofood.mvvm.activity.HomeSPActivity
import com.atlas.orianofood.mvvm.activity.PrivacyPolicyActivity
import com.atlas.orianofood.mvvm.activity.TermsAndCondition
import com.atlas.orianofood.mvvm.utils.logout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_subscription.*
import kotlinx.android.synthetic.main.app_bar_subscription.*
import kotlinx.android.synthetic.main.content_subscription.*

class SubscriptionActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private val activity = this@SubscriptionActivity

    private lateinit var adapter: SubscriptionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subscription)
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)
        val fab: FloatingActionButton = findViewById(R.id.fab)

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

        loadMenuItems()
    }

    private fun loadMenuItems() {
        val myListData: Array<Subscription> = arrayOf<Subscription>(
            Subscription(
                0, "Basic Plan", "Free", "1 month", "Basic Plan",
                "#01A5E1", "1. 5% discount on first order\n 2. First 3 free deliveries"
            ),
            Subscription(
                1, "Silver Plan", "₹299", "3 month", "Silver Plan",
                "#85838D", "1. 5% discount on orders above ₹800\n2. First 10 free deliveries"
            ),
            Subscription(
                2,
                "Gold Plan",
                "₹499",
                "6 month",
                "Gold Plan",
                "#AF9500",
                "1. 10% discount on orders above ₹800\n2. Get cashback and rewards on each order\n3. Unlimited free deliveries"
            )
        )

        adapter = SubscriptionAdapter(myListData)
        val adsManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerview.layoutManager = adsManager
        recyclerview.setHasFixedSize(true)
        recyclerview.adapter = adapter
        adapter.notifyDataSetChanged()
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
                /*val intent = Intent(activity, GalleryActivity::class.java)
                startActivity(intent)
                finish()*/
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

}
