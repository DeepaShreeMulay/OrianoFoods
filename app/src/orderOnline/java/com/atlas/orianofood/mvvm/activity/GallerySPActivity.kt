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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.viewpager.widget.ViewPager
import com.atlas.orianofood.R
import com.atlas.orianofood.firebaseRT.activity.*
import com.atlas.orianofood.firebaseRT.activity.ui.main.SectionsPagerAdapter
import com.atlas.orianofood.mvvm.gallery.adapter.GalleryAdapter
import com.atlas.orianofood.mvvm.gallery.model.GalleryViewModel
import com.atlas.orianofood.mvvm.utils.logout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.orderOnline.activity_gallery.*
import kotlinx.android.synthetic.orderOnline.app_bar_gallery.*
import kotlinx.android.synthetic.orderOnline.content_gallery.*

class GallerySPActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private val activity = this@GallerySPActivity

    private val viewModel: GalleryViewModel by lazy { ViewModelProvider(this).get(GalleryViewModel::class.java) }
    private lateinit var galleryAdapter: GalleryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)
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

        //user name
       /* val view: View = nav_view.getHeaderView(0)
        if (currentUser != null) {
            println(currentUser.displayName + "   " + currentUser.phoneNumber + "   " + currentUser.email)
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
        }*/

        val manager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        recyclerview.layoutManager = manager
        recyclerview.setHasFixedSize(true)
        loadMenuItems()
    }






    private fun loadMenuItems() {
        galleryAdapter= GalleryAdapter(mutableListOf())
        recyclerview.adapter = galleryAdapter

        with(viewModel) {
            galleryData.observe(this@GallerySPActivity, Observer {
                //  Toast.makeText(this@GallerySPActivity, "Productdata  run", Toast.LENGTH_SHORT).show()
                if (it!!.glist.isNotEmpty()) {

                    galleryAdapter.clear()
                    galleryAdapter.add(it.glist)

                } else {
                    Log.e("error", "error in connect with adapter")
                }
            })
            gshowToast.observe(this@GallerySPActivity, Observer {

                Toast.makeText(this@GallerySPActivity ,"$it", Toast.LENGTH_SHORT).show()
            })
            error.observe(this@GallerySPActivity, Observer {

                Toast.makeText(this@GallerySPActivity, "$it", Toast.LENGTH_SHORT).show()
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

/*override fun onResume() {
    super.onResume()
    loadMenuItems()
    viewHolder.startListening()
}

override fun onPause() {
    super.onPause()
    viewHolder.stopListening()
}

override fun onStart() {
    super.onStart()
    viewHolder.startListening()
}

override fun onStop() {
    super.onStop()
    viewHolder.stopListening()
}*/

    override fun onNavigationItemSelected(item: MenuItem): Boolean  {

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

}
