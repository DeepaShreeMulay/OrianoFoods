package com.atlas.orianofood.mvvm.activity


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.atlas.orianofood.R
import com.atlas.orianofood.firebaseRT.activity.*
import com.atlas.orianofood.firebaseRT.adapter.MyListAdapter
import com.atlas.orianofood.firebaseRT.utils.*
import com.atlas.orianofood.mvvm.category.adapter.CategoryAdapter
import com.atlas.orianofood.mvvm.category.model.CategoryViewModel
import com.atlas.orianofood.mvvm.getProfile.model.ProfileViewModel
import com.atlas.orianofood.mvvm.topCategory.adapter.TopCategoryAdapter
import com.atlas.orianofood.mvvm.topCategory.model.TopCategoryViewModel
import com.atlas.orianofood.mvvm.topRatedProduct.adapter.TopAdapter
import com.atlas.orianofood.mvvm.topRatedProduct.model.TopRatedViewModel
import com.atlas.orianofood.mvvm.topRatedSelling.adapter.SellingAdapter
import com.atlas.orianofood.mvvm.topRatedSelling.model.SellingViewModel
import com.atlas.orianofood.mvvm.utils.logout
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.gallery_item.view.*
import kotlinx.android.synthetic.orderOnline.activity_home.*
import kotlinx.android.synthetic.orderOnline.app_bar_home.*
import kotlinx.android.synthetic.orderOnline.content_addresses.*
import kotlinx.android.synthetic.orderOnline.content_home.*
import kotlinx.android.synthetic.orderOnline.content_home.recyclerview

class HomeSPActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private val activity = this
    private val viewModel: SellingViewModel by lazy { ViewModelProvider(this).get(SellingViewModel::class.java) }
    private lateinit var sellingAdapter: SellingAdapter
    lateinit var adapter: MyListAdapter




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

        /*val adsManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        adRecyclerView.layoutManager = adsManager
        adRecyclerView.setHasFixedSize(true)*/
        loadOffers()

        val topRatingManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        topRatingRecyclerview.layoutManager = topRatingManager
        topRatingRecyclerview.setHasFixedSize(true)
        loadTopRatingItems()  // Ads chodo Gallary
    }

    private val cviewModel: CategoryViewModel by lazy {
        ViewModelProvider(this).get(
            CategoryViewModel::class.java
        )
    }
    private lateinit var categoryadapter: CategoryAdapter

    private val tcviewModel: TopCategoryViewModel by lazy {
        ViewModelProvider(this).get(
            TopCategoryViewModel::class.java
        )
    }
    private lateinit var topCategoryAdapter: TopCategoryAdapter

    private fun loadMenuItems() {
        topCategoryAdapter = TopCategoryAdapter(activity, mutableListOf())
        recyclerview.adapter = topCategoryAdapter

        with(tcviewModel) {
            topCategoryData.observe(activity, Observer {
                Toast.makeText(activity, "Productdata  run", Toast.LENGTH_SHORT).show()
                if (it!!.tclist.isNotEmpty()) {

                    topCategoryAdapter.clear()
                    topCategoryAdapter.add(it.tclist)

                } else {
                    Log.e("error", "error in connect with adapter")
                }
            })
            tcshowToast.observe(activity, Observer {

                Toast.makeText(this@HomeSPActivity, "$it", Toast.LENGTH_SHORT).show()
            })
            error.observe(this@HomeSPActivity, Observer {

                Toast.makeText(this@HomeSPActivity, "$it", Toast.LENGTH_SHORT).show()
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

    private val tviewModel: TopRatedViewModel by lazy {
        ViewModelProvider(this).get(
            TopRatedViewModel::class.java
        )
    }
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

                Toast.makeText(this@HomeSPActivity, "$it", Toast.LENGTH_SHORT).show()
            })
            error.observe(this@HomeSPActivity, Observer {

                Toast.makeText(this@HomeSPActivity, "$it", Toast.LENGTH_SHORT).show()
            })

        }
    }



    private fun loadOffers() {
        val myListData: Array<String> = arrayOf<String>(
            "https://i.pinimg.com/564x/1f/e9/a4/1fe9a4401d9ad3b63ee0b4edc9d8ef79.jpg",
            "https://i.pinimg.com/originals/51/7b/51/517b51dd5d406bccba321863e4eab807.jpg",
            "https://i.pinimg.com/236x/54/26/2e/54262e075cadfd959fa18e7fc7eb973d.jpg"
        )

        adapter = MyListAdapter(myListData)
        val adsManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        adRecyclerView.layoutManager = adsManager
        adRecyclerView.setHasFixedSize(true)
        adRecyclerView.adapter = adapter
        adapter.notifyDataSetChanged()

    }

    private val profileviewModel: ProfileViewModel by lazy {
        ViewModelProvider(this).get(
            ProfileViewModel::class.java
        )
    }

    private fun loadProfileActivity() {
        with(profileviewModel) {
            profileData.observe(this@HomeSPActivity, Observer {
                Toast.makeText(this@HomeSPActivity, "Productdata  run", Toast.LENGTH_SHORT).show()
                /* if (it!!.userLogin.isNotEmpty()) {
                    Log.e("PTAG","${it.userLogin}")
                } else {
                    Log.e("error", "error in connect with adapter")
                }*/
            })
            profileshowToast.observe(this@HomeSPActivity, Observer {

                Toast.makeText(this@HomeSPActivity, "$it", Toast.LENGTH_SHORT).show()
            })
            error.observe(this@HomeSPActivity, Observer {

                Toast.makeText(this@HomeSPActivity, "$it", Toast.LENGTH_SHORT).show()
            })

        }
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
            R.id.nav_category -> {
                // Handle the camera action
                val intent = Intent(activity, CategorySPActivity::class.java)
                startActivity(intent)
                finish()
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

    fun intentCategory(view: View) {
        startActivity(Intent(this, CategorySPActivity::class.java))
        finish()
    }

}


