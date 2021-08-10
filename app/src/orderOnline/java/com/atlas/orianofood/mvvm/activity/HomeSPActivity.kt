package com.atlas.orianofood.mvvm.activity


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.atlas.orianofood.R
import com.atlas.orianofood.core.App
import com.atlas.orianofood.firebaseRT.activity.*
import com.atlas.orianofood.firebaseRT.adapter.MyListAdapter
import com.atlas.orianofood.firebaseRT.utils.*
import com.atlas.orianofood.mvvm.category.ShowCategoryData
import com.atlas.orianofood.mvvm.category.adapter.CategoryAdapter
import com.atlas.orianofood.mvvm.category.model.CategoryViewModel
import com.atlas.orianofood.mvvm.database.AppDatabase
import com.atlas.orianofood.mvvm.getProfile.model.ProfileViewModel
//import com.atlas.orianofood.mvvm.order.Actions
import com.atlas.orianofood.mvvm.product.model.ProductViewModel
import com.atlas.orianofood.mvvm.reciver.StateChangeReciver
import com.atlas.orianofood.mvvm.topCategory.adapter.TopCategoryAdapter
import com.atlas.orianofood.mvvm.topCategory.model.TopCategoryViewModel
import com.atlas.orianofood.mvvm.topRatedProduct.adapter.TopAdapter
import com.atlas.orianofood.mvvm.topRatedProduct.model.TopRatedItem
import com.atlas.orianofood.mvvm.topRatedProduct.model.TopRatedViewModel
import com.atlas.orianofood.mvvm.topRatedSelling.adapter.SellingAdapter
import com.atlas.orianofood.mvvm.topRatedSelling.model.SellingItem
import com.atlas.orianofood.mvvm.topRatedSelling.model.SellingViewModel
import com.atlas.orianofood.mvvm.utils.logout
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_my_cart.*
import kotlinx.android.synthetic.main.gallery_item.view.*
import kotlinx.android.synthetic.orderOnline.activity_home.*
import kotlinx.android.synthetic.orderOnline.activity_profile.*
import kotlinx.android.synthetic.orderOnline.app_bar_home.*
import kotlinx.android.synthetic.orderOnline.app_bar_home.toolbar
import kotlinx.android.synthetic.orderOnline.content_addresses.*
import kotlinx.android.synthetic.orderOnline.content_home.*
import kotlinx.android.synthetic.orderOnline.content_home.recyclerview
import kotlinx.android.synthetic.orderOnline.content_product.*
import kotlinx.android.synthetic.orderOnline.dialog_request.*
import kotlinx.android.synthetic.orderOnline.nav_header_home.*

class HomeSPActivity : AppCompatActivity(), ShowCategoryData,
    NavigationView.OnNavigationItemSelectedListener, StateChangeReciver.StateChangeListener {

    private val activity = this@HomeSPActivity
    private val viewModel: SellingViewModel by lazy { ViewModelProvider(this).get(SellingViewModel::class.java) }
    private lateinit var sellingItems: List<SellingItem>
    private lateinit var sellingAdapter: SellingAdapter
    lateinit var adapter: MyListAdapter

    val orderDao = AppDatabase.getInstance(App.appContext)?.orderDao!!

    val productDao = AppDatabase.getInstance(App.appContext)?.productDao!!


    // private val currentUser = LoginDataBase.getInstance(this).loginDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        HOMESPACTIVITYCONTEXT = this

        toolbar.title = ""
        setSupportActionBar(toolbar)

        loadProductItems()
        loadProfileActivity()


        fab.setOnClickListener {
            /* var username: String
             var userMobile: String
 */

            if (selectedProductIDsList.isEmpty()) {
                //Toast.makeText(this, "Please Add items in the cart", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, EmptyCart::class.java))
                finish()

            } else {
                val intent = Intent(
                    activity,
                    MyCartSpActivity::class.java
                )//ye karne ki jarrurat nhi thi database mese lena bhi chalta ok
                /* username = userLogged.text.toString()
                 userMobile = userLoggedMobile.text.toString()

                 intent.putExtra("UserName", username)
                 intent.putExtra("UserMobile", userMobile)*/
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

        val manager = GridLayoutManager(this, 4)
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
                //Toast.makeText(activity, "Productdata  run", Toast.LENGTH_SHORT).show()
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
            error.observe(activity, Observer {

                Toast.makeText(this@HomeSPActivity, "$it", Toast.LENGTH_SHORT).show()
            })

        }

    }

    private val pviewModel: ProductViewModel by lazy { ViewModelProvider(this).get(ProductViewModel::class.java) }

    private fun loadProductItems() {


        with(pviewModel) {
            productData.observe(activity, Observer {
                if (it!!.plist.isNotEmpty()) {
                    //appDatabase.productDao.selectDataByCategoryId(selectedCategoryId!!)
                    Log.e("TAG", "${it.plist}")

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


    private fun loadTopSellingItems() {
        sellingAdapter = SellingAdapter(this@HomeSPActivity, mutableListOf())
        topSellingRecyclerview.adapter = sellingAdapter

        with(viewModel) {
            sellingData.observe(this@HomeSPActivity, Observer {
                //  Toast.makeText(this@HomeSPActivity, "Productdata  run", Toast.LENGTH_SHORT).show()
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
        ViewModelProvider(this).get(TopRatedViewModel::class.java)
    }
    private lateinit var topRatedItem: List<TopRatedItem>
    private lateinit var topAdapter: TopAdapter
    private var productIds = ArrayList<Int>()
    private fun loadTopRatingItems() {

        topAdapter = TopAdapter(
            orderDao, this@HomeSPActivity, mutableListOf()
        )
        topRatingRecyclerview.adapter = topAdapter
        with(tviewModel) {
            topRatedData.observe(this@HomeSPActivity, Observer {
                // Toast.makeText(this@HomeSPActivity, "Productdata  run", Toast.LENGTH_SHORT).show()
                if (it!!.rlist.isNotEmpty()) {

                    topAdapter.clear()
                    // topRatedItem=it.rlist
                    //topRatedItem.forEach { item -> productIds.add(item.productId) }
                    //val products = productDao.selectDataByProductIdList(productIds)
                    // topAdapter.add(it.rlist)
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
                // Toast.makeText(this@HomeSPActivity, "Productdata  run", Toast.LENGTH_SHORT).show()
                if (it!!.userLogin.isNotEmpty()) {
                    Log.e("PTAG", "${it.userLogin}")
                    userLogged.text = "" + it.userLogin


                }
                if (it.userEmail.isNotEmpty()) {
                    userLoggedMobile.text = "${it.userEmail}"
                } else {
                    userLoggedMobile.setVisible(false)
                }

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
                if (selectedProductIDsList.isEmpty()) {
                    Toast.makeText(this, "Please Add items in the cart", Toast.LENGTH_SHORT).show()
                } else {
                    val intent = Intent(activity, MyCartSpActivity::class.java)
                    startActivity(intent)


                }
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
                //finish()
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

    fun intentCategory(view: View) {
        startActivity(Intent(this, CategorySPActivity::class.java))
        finish()
    }

    override fun transferCategoryData(data: String) {
        val intent = Intent(this, ProductSPActivity::class.java)
        intent.putExtra("CategoryID", data)
        startActivity(intent)
    }

    /* override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
         super.onActivityResult(requestCode, resultCode, data)
         if (requestCode == 2) {
             profileName = data?.getStringExtra("display_mobile").toString()
             text_phone.setText(profileName)
             //  Toast.makeText(this,"select Address",Toast.LENGTH_SHORT).show()

         }
     }*/
    fun View.setVisible(visible: Boolean) {
        visibility = if (visible) {
            Button.VISIBLE
        } else {
            Button.GONE
        }
    }

    override fun onStateChanged(sessionStates: String?) {
        /*   for (i in 0 until topRatedItem.size) {
               if (changedQuantity.containsKey(topRatedItem[i])) {
                   topAdapter.updateElegentButton(i)
               }
           }
           for (i in 0 until sellingItems.size) {
               if (changedQuantity.containsKey(sellingItems[i])) {
                   sellingAdapter.updateElegentButton(i)
               }
           }*/
        topAdapter.notifyDataSetChanged()
        sellingAdapter.notifyDataSetChanged()
    }


}



