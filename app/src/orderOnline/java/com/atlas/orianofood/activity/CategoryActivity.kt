package com.atlas.orianofood.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
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
import com.atlas.orianofood.adapter.OffersViewHolder
import com.atlas.orianofood.adapter.ProductViewHolder
import com.atlas.orianofood.interfaces.ItemClickListener
import com.atlas.orianofood.model.Category
import com.atlas.orianofood.model.Offer
import com.atlas.orianofood.model.ProductCategory
import com.atlas.orianofood.utils.CATEGORY_EXTRA
import com.atlas.orianofood.utils.Common
import com.atlas.orianofood.utils.OFFERS_EXTRA
import com.atlas.orianofood.utils.PRODUCT_CATEGORY_EXTRA
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.android.material.navigation.NavigationView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.orderOnline.activity_home.*
import kotlinx.android.synthetic.orderOnline.app_bar_home.*
import kotlinx.android.synthetic.orderOnline.content_home.*
import kotlinx.android.synthetic.orderOnline.nav_header_home.view.*
import java.text.NumberFormat
import java.util.*


class CategoryActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var database: FirebaseDatabase
    lateinit var productdatabase: FirebaseDatabase
    lateinit var menu: DatabaseReference
    lateinit var offer: DatabaseReference
    lateinit var topSelling: DatabaseReference
    lateinit var topRated: DatabaseReference
    lateinit var viewHolder1: FirebaseRecyclerAdapter<Category, MenuViewHolder>
    lateinit var topRatedViewHolder1: FirebaseRecyclerAdapter<ProductCategory, ProductViewHolder>
    lateinit var topSellingViewHolder1: FirebaseRecyclerAdapter<ProductCategory, ProductViewHolder>
    lateinit var offersViewHolder: FirebaseRecyclerAdapter<Offer, OffersViewHolder>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        toolbar.title = ""
        setSupportActionBar(toolbar)
        textView2.text = CATEGORY_EXTRA


        //firebase init
        database = FirebaseDatabase.getInstance()
        menu = database.getReference(CATEGORY_EXTRA)
        offer = database.getReference(OFFERS_EXTRA)
        topSelling = database.getReference(PRODUCT_CATEGORY_EXTRA)
        topRated = database.getReference(PRODUCT_CATEGORY_EXTRA)
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

        val manager = GridLayoutManager(this, 2)
        recyclerview.layoutManager = manager
        recyclerview.setHasFixedSize(true)
        loadCategoryItems()

        val offerManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        topSellingRecyclerview.layoutManager = offerManager
        topSellingRecyclerview.setHasFixedSize(true)
        loadTopSellingItems()

        val adsManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        adRecyclerView.layoutManager = adsManager
        adRecyclerView.setHasFixedSize(true)
        loadOffers()

        val topRatingManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        topRatingRecyclerview.layoutManager = topRatingManager
        topRatingRecyclerview.setHasFixedSize(true)
        loadTopRatingItems()

    }


    private fun loadTopSellingItems() {
        val productQuery = topSelling.orderByKey()
        val productsOption = FirebaseRecyclerOptions.Builder<ProductCategory>()
            .setQuery(topSelling, ProductCategory::class.java).build()

        topSellingViewHolder1 =
            object : FirebaseRecyclerAdapter<ProductCategory, ProductViewHolder>(
                productsOption
            ) {
                override fun onCreateViewHolder(
                    parent: ViewGroup,
                    viewType: Int
                ): ProductViewHolder {
                    val view = LayoutInflater.from(parent.context)
                        .inflate(R.layout.top_product_cardview_item, parent, false)

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

                    val locale = Locale("en", "IN")
                    val nf = NumberFormat.getCurrencyInstance(locale)

                    holder.name.text = model.name
                    holder.rate.text = "${nf.format(model.rate?.toDouble())}"

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
                                topSellingViewHolder1.getRef(position).key
                            )
                            startActivity(intent)
                            finish()
                        }
                    }
                    holder.setitemClickListener(itemClickListener)
                }
            }
        topSellingRecyclerview.adapter = topSellingViewHolder1
    }

    private fun loadTopRatingItems() {
        val productQuery = topRated.orderByKey()
        val productsOption = FirebaseRecyclerOptions.Builder<ProductCategory>()
            .setQuery(topRated, ProductCategory::class.java).build()

        topRatedViewHolder1 = object : FirebaseRecyclerAdapter<ProductCategory, ProductViewHolder>(
            productsOption
        ) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.top_product_cardview_item, parent, false)

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

                val locale = Locale("en", "IN")
                val nf = NumberFormat.getCurrencyInstance(locale)

                holder.name.text = model.name
                holder.rate.text = "${nf.format(model.rate?.toDouble())}"

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
                            topRatedViewHolder1.getRef(position).key
                        )
                        startActivity(intent)
                        finish()
                    }
                }
                holder.setitemClickListener(itemClickListener)
            }
        }
        topRatingRecyclerview.adapter = topRatedViewHolder1
    }

    private fun loadOffers() {
        val offerQuery = offer.orderByKey()
        val offerOption = FirebaseRecyclerOptions.Builder<Offer>()
            .setQuery(offer, Offer::class.java).build()

        offersViewHolder = object : FirebaseRecyclerAdapter<Offer, OffersViewHolder>(
            offerOption
        ) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OffersViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.offer_card_view, parent, false)

                val img = view.findViewById<ImageView>(R.id.adView)
                return OffersViewHolder(view, img)
            }

            override fun onBindViewHolder(
                holder: OffersViewHolder,
                position: Int,
                model: Offer
            ) {

                Picasso.get()
                    .load(model.image)
                    .placeholder(R.mipmap.bg_home)
                    .error(R.mipmap.bg_home)
                    .into(holder.img)

                /*val itemClickListener = object : ItemClickListener {
                    override fun onClick(view: View, position: Int, isLongClick: Boolean) {
                        val intent = Intent(this@HomeActivity, ProductActivity::class.java)
                        intent.putExtra(
                            PRODUCT_CATEGORY_EXTRA,
                            offersViewHolder.getRef(position).key
                        )
                        startActivity(intent)
                        finish()
                    }
                }
                holder.setitemClickListener(itemClickListener)*/
            }
        }
        adRecyclerView.adapter = offersViewHolder
        adRecyclerView.smoothScrollToPosition(offersViewHolder.itemCount)
    }

    private fun loadCategoryItems() {
        val menuQuery = menu.orderByKey()
        val menuOption = FirebaseRecyclerOptions.Builder<Category>()
            .setQuery(menu, Category::class.java).build()

        viewHolder1 = object : FirebaseRecyclerAdapter<Category, MenuViewHolder>(menuOption) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.category_cardview_item, parent, false)

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
            val intent = Intent(this@CategoryActivity, HomeActivity::class.java)
            startActivity(intent)
            finish()
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
        loadCategoryItems()
        loadTopSellingItems()
        loadTopRatingItems()
        loadOffers()
        viewHolder1.startListening()
        topRatedViewHolder1.startListening()
        topSellingViewHolder1.startListening()
        offersViewHolder.startListening()
    }

    override fun onPause() {
        super.onPause()
        viewHolder1.stopListening()
        topSellingViewHolder1.stopListening()
        topRatedViewHolder1.stopListening()
        offersViewHolder.stopListening()
    }

    override fun onStart() {
        super.onStart()
        viewHolder1.startListening()
        topRatedViewHolder1.startListening()
        topSellingViewHolder1.startListening()
        offersViewHolder.startListening()
    }

    override fun onStop() {
        super.onStop()
        viewHolder1.stopListening()
        topRatedViewHolder1.stopListening()
        topSellingViewHolder1.stopListening()
        offersViewHolder.stopListening()
    }

    fun Any.toast(context: Context, duration: Int = Toast.LENGTH_SHORT): Toast {
        return Toast.makeText(context, this.toString(), duration).apply { show() }
    }


}
