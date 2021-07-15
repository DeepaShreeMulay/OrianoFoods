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
import com.atlas.orianofood.firebaseRT.adapter.AddressAdapter
import com.atlas.orianofood.firebaseRT.database.DatabaseHandler
import com.atlas.orianofood.firebaseRT.interfaces.SendData
import com.atlas.orianofood.firebaseRT.model.Address
import com.atlas.orianofood.mvvm.activity.HomeSPActivity
import com.atlas.orianofood.mvvm.activity.PrivacyPolicyActivity
import com.atlas.orianofood.mvvm.activity.TermsAndCondition
import com.atlas.orianofood.mvvm.utils.logout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.orderOnline.activity_addresses.*
import kotlinx.android.synthetic.orderOnline.address_item.*
import kotlinx.android.synthetic.orderOnline.app_bar_addresses.*
import kotlinx.android.synthetic.orderOnline.content_addresses.*
import kotlinx.android.synthetic.orderOnline.nav_header_home.view.*


class MyAddressesActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, SendData {

    lateinit var adapter: AddressAdapter
    lateinit var addresslist: List<Address>

    private val currentUser = FirebaseAuth.getInstance().currentUser
    private val activity = this@MyAddressesActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addresses)
        toolbar.title = ""
        setSupportActionBar(toolbar)

        fab.setOnClickListener {
            val intent = Intent(activity, AddAddressActivity::class.java)
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
                !currentUser.displayName.isNullOrEmpty() -> {
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

        loadAddresses()
    }

    private fun loadAddresses() {
        DatabaseHandler(this).createAddressTable()
        addresslist = DatabaseHandler(this).getAddresses()
        if (addresslist.isEmpty()) {
            startActivity(Intent(this, AddAddressActivity::class.java))
            //tvNoAddress.visibility = View.VISIBLE
            recyclerview.visibility = View.INVISIBLE
        } else {
            tvNoAddress.visibility = View.INVISIBLE
            recyclerview.visibility = View.VISIBLE
            adapter = AddressAdapter(this, addresslist)
            val manager = LinearLayoutManager(this)
            recyclerview.layoutManager = manager
            recyclerview.setHasFixedSize(true)
            recyclerview.adapter = adapter
        }
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_home -> {
                // Handle the camera action
                val intent = Intent(activity, HomeSPActivity::class.java)
                startActivity(intent)
                finish()
            }
            R.id.nav_address -> {
                // Handle the camera action
                /*val intent = Intent(activity, MyAddressesActivity::class.java)
                startActivity(intent)
                finish()*/
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

    override fun address(address: String) {
        // val address: String =tv_addressline1.text.toString()

        val intent = Intent()
        intent.putExtra("Address", address)


        setResult(2, intent)

        finishActivity(2)
    }
    /*override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
            val intent = Intent(this@MyAddressesActivity, HomeSPActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
*/

}