package com.atlas.orianofood.firebaseRT.activity

import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.atlas.orianofood.R
import com.atlas.orianofood.firebaseRT.adapter.OffersViewHolder
import com.atlas.orianofood.firebaseRT.adapter.ProductViewHolder
import com.atlas.orianofood.firebaseRT.interfaces.ItemClickListener
import com.atlas.orianofood.firebaseRT.model.Offer
import com.atlas.orianofood.firebaseRT.model.ProductCategory
import com.atlas.orianofood.firebaseRT.utils.OFFERS_EXTRA
import com.atlas.orianofood.firebaseRT.utils.PRODUCT_CATEGORY_EXTRA
import com.atlas.orianofood.firebaseRT.utils.logout
import com.atlas.orianofood.firebaseRT.utils.toast
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.orderOnline.activity_home.*
import kotlinx.android.synthetic.orderOnline.app_bar_home.*
import kotlinx.android.synthetic.orderOnline.content_home.*
import kotlinx.android.synthetic.orderOnline.nav_header_home.view.*
import java.text.NumberFormat
import java.util.*

class ProductCategoryActivity : AppCompatActivity(),
    NavigationView.OnNavigationItemSelectedListener {

    lateinit var database: FirebaseDatabase
    lateinit var menu: DatabaseReference
    lateinit var offer: DatabaseReference
    lateinit var topSelling: DatabaseReference
    lateinit var topRated: DatabaseReference
    lateinit var topRatedViewHolder2: FirebaseRecyclerAdapter<ProductCategory, ProductViewHolder>
    lateinit var topSellingViewHolder2: FirebaseRecyclerAdapter<ProductCategory, ProductViewHolder>
    lateinit var offersViewHolder: FirebaseRecyclerAdapter<Offer, OffersViewHolder>
    lateinit var viewHolder2: FirebaseRecyclerAdapter<ProductCategory, ProductViewHolder>

    private val currentUser = FirebaseAuth.getInstance().currentUser
    private val activity = this@ProductCategoryActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        toolbar.title = ""
        setSupportActionBar(toolbar)

        //firebase init
        database = FirebaseDatabase.getInstance()
        menu = database.getReference(PRODUCT_CATEGORY_EXTRA)
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
        }

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
        val productQuery = topSelling.orderByChild("offer").equalTo("Top Selling")
        val productsOption = FirebaseRecyclerOptions.Builder<ProductCategory>()
            .setQuery(productQuery, ProductCategory::class.java).build()

        topSellingViewHolder2 =
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
                    val sellingPrice = view.findViewById<TextView>(R.id.productSellingprice)
                    val saleTag = view.findViewById<ImageView>(R.id.sale_tag)
                    val layout = view.findViewById<ConstraintLayout>(R.id.constraintLayout)
                    return ProductViewHolder(view, img, name, rate, sellingPrice, saleTag, layout)
                }

            override fun onBindViewHolder(
                holder: ProductViewHolder,
                position: Int,
                model: ProductCategory
            ) {

                val locale = Locale("en", "IN")
                val nf = NumberFormat.getCurrencyInstance(locale)

                holder.name.text = model.name

                holder.rate.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                holder.rate.text = "${nf.format(model.rate?.toDouble())}"
                holder.sellingPrice.text = "${nf.format(model.sellingprice?.toDouble())}"

                if (model.isSale != null && model.isSale.equals("yes")) {
                    holder.saleTag.visibility = View.VISIBLE
                } else {
                    val lp: LinearLayout.LayoutParams =
                        LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                        )
                    holder.layout.layoutParams = lp
                }

                Picasso.get()
                    .load(model.image)
                    .resize(300, 300)
                    .placeholder(R.drawable.ic_dish)
                    .error(R.drawable.ic_dish)
                    .into(holder.img)

                val itemClickListener = object : ItemClickListener {
                    override fun onClick(view: View, position: Int, isLongClick: Boolean) {
                        val intent =
                            Intent(this@ProductCategoryActivity, ProductActivity::class.java)
                        intent.putExtra(
                            PRODUCT_CATEGORY_EXTRA,
                            topSellingViewHolder2.getRef(position).key
                        )
                        startActivity(intent)
                        finish()
                    }
                }
                holder.setitemClickListener(itemClickListener)
            }
            }
        topSellingRecyclerview.adapter = topSellingViewHolder2
    }

    private fun loadTopRatingItems() {
        val productQuery = topRated.orderByKey()
        val productsOption = FirebaseRecyclerOptions.Builder<ProductCategory>()
            .setQuery(topRated, ProductCategory::class.java).build()

        topRatedViewHolder2 = object : FirebaseRecyclerAdapter<ProductCategory, ProductViewHolder>(
            productsOption
        ) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.top_product_cardview_item, parent, false)

                val name = view.findViewById<TextView>(R.id.productName)
                val img = view.findViewById<ImageView>(R.id.productImage)
                val rate = view.findViewById<TextView>(R.id.productRate)
                val sellingPrice = view.findViewById<TextView>(R.id.productSellingprice)
                val saleTag = view.findViewById<ImageView>(R.id.sale_tag)
                val layout = view.findViewById<ConstraintLayout>(R.id.constraintLayout)
                return ProductViewHolder(view, img, name, rate, sellingPrice, saleTag, layout)
            }

            override fun onBindViewHolder(
                holder: ProductViewHolder,
                position: Int,
                model: ProductCategory
            ) {

                val locale = Locale("en", "IN")
                val nf = NumberFormat.getCurrencyInstance(locale)

                holder.name.text = model.name

                holder.rate.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                holder.rate.text = "${nf.format(model.rate?.toDouble())}"
                holder.sellingPrice.text = "${nf.format(model.sellingprice?.toDouble())}"

                if (model.isSale != null && model.isSale.equals("yes")) {
                    holder.saleTag.visibility = View.VISIBLE
                } else {
                    val lp: LinearLayout.LayoutParams =
                        LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                        )
                    holder.layout.layoutParams = lp
                }

                Picasso.get()
                    .load(model.image)
                    .resize(300, 300)
                    .placeholder(R.drawable.ic_dish)
                    .error(R.drawable.ic_dish)
                    .into(holder.img)

                val itemClickListener = object : ItemClickListener {
                    override fun onClick(view: View, position: Int, isLongClick: Boolean) {
                        val intent =
                            Intent(this@ProductCategoryActivity, ProductActivity::class.java)
                        intent.putExtra(
                            PRODUCT_CATEGORY_EXTRA,
                            topRatedViewHolder2.getRef(position).key
                        )
                        startActivity(intent)
                        finish()
                    }
                }
                holder.setitemClickListener(itemClickListener)
            }
        }
        topRatingRecyclerview.adapter = topRatedViewHolder2
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
                    .resize(300, 300)
                    .placeholder(R.drawable.ic_dish)
                    .error(R.drawable.ic_dish)
                    .into(holder.img)

                val itemClickListener = object : ItemClickListener {
                    override fun onClick(view: View, position: Int, isLongClick: Boolean) {
                        toast("Work in Progress. Soon you will see this feature.")
                        /*val intent = Intent(this@HomeActivity, ProductActivity::class.java)
                        intent.putExtra(
                            PRODUCT_CATEGORY_EXTRA,
                            offersViewHolder.getRef(position).key
                        )
                        startActivity(intent)
                        finish()*/
                    }
                }
                holder.setitemClickListener(itemClickListener)
            }
        }
        adRecyclerView.adapter = offersViewHolder
        adRecyclerView.smoothScrollToPosition(offersViewHolder.itemCount)
    }

    private fun loadCategoryItems() {
        val menuQuery = menu.orderByKey()
        val menuOption = FirebaseRecyclerOptions.Builder<ProductCategory>()
            .setQuery(menu, ProductCategory::class.java).build()

        viewHolder2 = object : FirebaseRecyclerAdapter<ProductCategory, ProductViewHolder>(
            menuOption
        ) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.product_category_cardview_item, parent, false)

                val name = view.findViewById<TextView>(R.id.productName)
                val img = view.findViewById<ImageView>(R.id.productImage)
                val rate = view.findViewById<TextView>(R.id.productRate)
                val sellingPrice = view.findViewById<TextView>(R.id.productSellingprice)
                val saleTag = view.findViewById<ImageView>(R.id.sale_tag)
                val layout = view.findViewById<ConstraintLayout>(R.id.constraintLayout)
                return ProductViewHolder(view, img, name, rate, sellingPrice, saleTag, layout)
            }

            override fun onBindViewHolder(
                holder: ProductViewHolder,
                position: Int,
                model: ProductCategory
            ) {

                val locale = Locale("en", "IN")
                val nf = NumberFormat.getCurrencyInstance(locale)

                holder.name.text = model.name

                holder.rate.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                holder.rate.text = "${nf.format(model.rate?.toDouble())}"
                holder.sellingPrice.text = "${nf.format(model.sellingprice?.toDouble())}"

                if (model.isSale != null && model.isSale.equals("yes")) {
                    holder.saleTag.visibility = View.VISIBLE
                } else {
                    val lp: LinearLayout.LayoutParams =
                        LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                        )
                    holder.layout.layoutParams = lp
                }

                Picasso.get()
                    .load(model.image)
                    .resize(300, 300)
                    .placeholder(R.drawable.ic_dish)
                    .error(R.drawable.ic_dish)
                    .into(holder.img)

                val itemClickListener = object : ItemClickListener {
                    override fun onClick(view: View, position: Int, isLongClick: Boolean) {
                        val intent =
                            Intent(this@ProductCategoryActivity, ProductActivity::class.java)
                        intent.putExtra(PRODUCT_CATEGORY_EXTRA, viewHolder2.getRef(position).key)
                        startActivity(intent)
                        finish()
                    }
                }
                holder.setitemClickListener(itemClickListener)
            }
        }
        recyclerview.adapter = viewHolder2
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
            val intent = Intent(this@ProductCategoryActivity, HomeActivity::class.java)
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

    override fun onResume() {
        super.onResume()
        loadCategoryItems()
        loadTopSellingItems()
        loadTopRatingItems()
        loadOffers()
        viewHolder2.startListening()
        topRatedViewHolder2.startListening()
        topSellingViewHolder2.startListening()
        offersViewHolder.startListening()
    }

    override fun onPause() {
        super.onPause()
        viewHolder2.stopListening()
        topSellingViewHolder2.stopListening()
        topRatedViewHolder2.stopListening()
        offersViewHolder.stopListening()
    }

    override fun onStart() {
        super.onStart()
        viewHolder2.startListening()
        topRatedViewHolder2.startListening()
        topSellingViewHolder2.startListening()
        offersViewHolder.startListening()
    }

    override fun onStop() {
        super.onStop()
        viewHolder2.stopListening()
        topRatedViewHolder2.stopListening()
        topSellingViewHolder2.stopListening()
        offersViewHolder.stopListening()
    }

    fun Any.toast(context: Context, duration: Int = Toast.LENGTH_SHORT): Toast {
        return Toast.makeText(context, this.toString(), duration).apply { show() }
    }


}
