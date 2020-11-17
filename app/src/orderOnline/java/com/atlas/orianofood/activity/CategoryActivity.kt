package com.atlas.orianofood.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.atlas.orianofood.R
import com.atlas.orianofood.adapter.MenuViewHolder
import com.atlas.orianofood.adapter.ProductViewHolder
import com.atlas.orianofood.interfaces.ItemClickListener
import com.atlas.orianofood.model.Category
import com.atlas.orianofood.model.ProductCategory
import com.atlas.orianofood.utils.CATEGORY_EXTRA
import com.atlas.orianofood.utils.Common
import com.atlas.orianofood.utils.PRODUCT_CATEGORY_EXTRA
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.android.material.navigation.NavigationView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.orderOnline.activity_home.*
import kotlinx.android.synthetic.orderOnline.app_bar_home.*
import kotlinx.android.synthetic.orderOnline.content_home.*
import kotlinx.android.synthetic.orderOnline.nav_header_home.view.*


class CategoryActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var database: FirebaseDatabase
    lateinit var productdatabase: FirebaseDatabase
    lateinit var menu: DatabaseReference
    lateinit var products: DatabaseReference
    lateinit var viewHolder1: FirebaseRecyclerAdapter<Category, MenuViewHolder>
    lateinit var productViewHolder1: FirebaseRecyclerAdapter<ProductCategory, ProductViewHolder>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        toolbar.title = CATEGORY_EXTRA
        setSupportActionBar(toolbar)
        textView2.text = CATEGORY_EXTRA


        //firebase init
        database = FirebaseDatabase.getInstance()
        menu = database.getReference(CATEGORY_EXTRA)
        productdatabase = FirebaseDatabase.getInstance()
        products = productdatabase.getReference(PRODUCT_CATEGORY_EXTRA)
        fab.setOnClickListener {
            val intent = Intent(this, CartActivity::class.java)
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
        view.userLogged.text = Common.currentUser!!.name


        // Initialize the Mobile Ads SDK
        MobileAds.initialize(this) { Log.e("AdMob", "Successful") }

        val manager = GridLayoutManager(this, 2)
        recyclerview.layoutManager = manager
        recyclerview.setHasFixedSize(true)
        loadCategoryItems()

        val offerManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        topSellingRecyclerview.layoutManager = offerManager
        topSellingRecyclerview.setHasFixedSize(true)
        loadTopSellingItems()

        val adRequest1: AdRequest = AdRequest.Builder().build()
        adView1.loadAd(adRequest1)
        val adRequest2: AdRequest = AdRequest.Builder().build()
        adView2.loadAd(adRequest2)

    }

    private fun loadTopSellingItems() {
        val productQuery = products.orderByKey()
        val productsOption = FirebaseRecyclerOptions.Builder<ProductCategory>()
            .setQuery(products, ProductCategory::class.java).build()

        productViewHolder1 = object : FirebaseRecyclerAdapter<ProductCategory, ProductViewHolder>(
            productsOption
        ) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.product_category_card_view, parent, false)

                val name = view.findViewById<TextView>(R.id.productName)
                val img = view.findViewById<ImageView>(R.id.productImage)
                val rate = view.findViewById<TextView>(R.id.productRate)
                return ProductViewHolder(view, img, name, rate)
            }

            override fun onBindViewHolder(
                holder: ProductViewHolder,
                position: Int,
                model: ProductCategory
            ) {
                holder.name.text = model.name
                holder.rate.text = "${model.rate}/dish"

                Picasso.get()
                    .load(model.image)
                    .placeholder(R.mipmap.bg_home)
                    .error(R.mipmap.bg_home)
                    .into(holder.img)

                val itemClickListener = object : ItemClickListener {
                    override fun onClick(view: View, position: Int, isLongClick: Boolean) {
                        val intent = Intent(this@CategoryActivity, ProductActivity::class.java)
                        intent.putExtra(
                            PRODUCT_CATEGORY_EXTRA,
                            productViewHolder1.getRef(position).key
                        )
                        startActivity(intent)
                        finish()
                    }
                }
                holder.setitemClickListener(itemClickListener)
            }
        }
        topSellingRecyclerview.adapter = productViewHolder1
    }

    private fun loadCategoryItems() {
        val menuQuery = menu.orderByKey()
        val menuOption = FirebaseRecyclerOptions.Builder<Category>()
            .setQuery(menu, Category::class.java).build()

        viewHolder1 = object : FirebaseRecyclerAdapter<Category, MenuViewHolder>(menuOption) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.category_card_view, parent, false)

                val name = view.findViewById<TextView>(R.id.categoryName)
                val img = view.findViewById<ImageView>(R.id.categoryImage)
                return MenuViewHolder(view, img, name)
            }

            override fun onBindViewHolder(holder: MenuViewHolder, position: Int, model: Category) {
                holder.categoryName.text = model.name

                Picasso.get()
                    .load(model.image)
                    .placeholder(R.mipmap.bg_home)
                    .error(R.mipmap.bg_home)
                    .into(holder.categoryImg)

                val itemClickListener = object : ItemClickListener {
                    override fun onClick(view: View, position: Int, isLongClick: Boolean) {
                        val intent =
                            Intent(this@CategoryActivity, ProductCategoryActivity::class.java)
                        intent.putExtra(CATEGORY_EXTRA, viewHolder1.getRef(position).key)
                        startActivity(intent)
                        finish()
                    }
                }
                holder.setitemClickListener(itemClickListener)
            }
        }
        recyclerview.adapter = viewHolder1
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    /*override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.home, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }*/

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_menu -> {
                // Handle the camera action
            }
            R.id.nav_cart -> {
                // Handle the camera action
            }
            R.id.nav_orders -> {
                // Handle the camera action
            }
            R.id.nav_log_out -> {
                // Handle the camera action
            }

        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onResume() {
        super.onResume()
        loadTopSellingItems()
        viewHolder1.startListening()
        productViewHolder1.startListening()
    }

    override fun onPause() {
        super.onPause()
        viewHolder1.stopListening()
        productViewHolder1.stopListening()
    }

    override fun onStart() {
        super.onStart()
        viewHolder1.startListening()
        productViewHolder1.startListening()
    }

    override fun onStop() {
        super.onStop()
        viewHolder1.stopListening()
        productViewHolder1.stopListening()
    }

    fun Any.toast(context: Context, duration: Int = Toast.LENGTH_SHORT): Toast {
        return Toast.makeText(context, this.toString(), duration).apply { show() }
    }


}
