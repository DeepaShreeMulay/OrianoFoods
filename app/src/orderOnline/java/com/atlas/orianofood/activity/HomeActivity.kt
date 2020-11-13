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
import com.atlas.orianofood.model.Menu
import com.atlas.orianofood.model.ProductCategory
import com.atlas.orianofood.utils.*
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.android.material.navigation.NavigationView
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.orderOnline.activity_home.*
import kotlinx.android.synthetic.orderOnline.app_bar_home.*
import kotlinx.android.synthetic.orderOnline.content_home.*
import kotlinx.android.synthetic.orderOnline.nav_header_home.view.*


class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var database: FirebaseDatabase
    lateinit var productdatabase: FirebaseDatabase
    lateinit var menu: DatabaseReference
    lateinit var products: DatabaseReference
    lateinit var viewHolder: FirebaseRecyclerAdapter<Menu, MenuViewHolder>
    lateinit var productViewHolder: FirebaseRecyclerAdapter<ProductCategory, ProductViewHolder>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        toolbar.title = "Menu"
        setSupportActionBar(toolbar)

        addDataToFirebaseDBMenu()
        addDataToFirebaseDBCategory()
        addDataToFirebaseDBProductCategory()

        //firebase init
        database = FirebaseDatabase.getInstance()
        menu = database.getReference(MENU_EXTRA)
        productdatabase = FirebaseDatabase.getInstance()
        products = productdatabase.getReference(PRODUCT_CATEGORY_EXTRA)
        fab.setOnClickListener {
            val intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
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
        loadMenuItems()

        val offerManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        topSellingRecyclerview.layoutManager = offerManager
        topSellingRecyclerview.setHasFixedSize(true)
        loadTopSellingItems()

        val adRequest1: AdRequest = AdRequest.Builder().build()
        adView1.loadAd(adRequest1)
        val adRequest2: AdRequest = AdRequest.Builder().build()
        adView2.loadAd(adRequest2)

    }

    private fun addDataToFirebaseDBProductCategory() {
        val database: FirebaseDatabase = FirebaseDatabase.getInstance()
        val productCategoryTable: DatabaseReference = database.getReference(PRODUCT_CATEGORY_EXTRA)
        val valueEventListener = object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                //Get User Information

                if (dataSnapshot.hasChildren()) {
                } else {

                    productCategoryTable.child("Paneer Tikka").setValue(
                        ProductCategory(
                            "Paneer Tikka",
                            "https://orianofood.online/wp-content/uploads/2020/08/Appetizers-300x300.jpg",
                            PRODUCT_EXTRA,
                            "Starter",
                            "Indian",
                            "140",
                            TOP_SELLING
                        )
                    )
                    productCategoryTable.child("Masala Papad").setValue(
                        ProductCategory(
                            "Masala Papad",
                            "https://orianofood.online/wp-content/uploads/2020/08/Appetizers-300x300.jpg",
                            PRODUCT_EXTRA,
                            "Starter",
                            "Indian",
                            "50"
                        )
                    )
                    productCategoryTable.child("Onion rings").setValue(
                        ProductCategory(
                            "Onion rings",
                            "https://orianofood.online/wp-content/uploads/2020/08/Appetizers-300x300.jpg",
                            PRODUCT_EXTRA,
                            "Starter",
                            "Indian",
                            "70",
                            TOP_SELLING
                        )
                    )

                    productCategoryTable.child("Rumali Roti").setValue(
                        ProductCategory(
                            "Rumali Roti",
                            "https://www.budgetbytes.com/wp-content/uploads/2010/09/Homemade-Naan-stack-1.jpg",
                            PRODUCT_EXTRA,
                            "Indian Breads",
                            "Indian",
                            "45"
                        )
                    )
                    productCategoryTable.child("Naan").setValue(
                        ProductCategory(
                            "Naan",
                            "https://www.budgetbytes.com/wp-content/uploads/2010/09/Homemade-Naan-stack-1.jpg",
                            PRODUCT_EXTRA,
                            "Indian Breads",
                            "Indian",
                            "45"
                        )
                    )
                    productCategoryTable.child("Wheat Roti").setValue(
                        ProductCategory(
                            "Wheat Roti",
                            "https://www.budgetbytes.com/wp-content/uploads/2010/09/Homemade-Naan-stack-1.jpg",
                            PRODUCT_EXTRA,
                            "Indian Breads",
                            "Indian",
                            "20"
                        )
                    )
                    productCategoryTable.child("Shahi Paneer").setValue(
                        ProductCategory(
                            "Shahi Paneer",
                            "https://www.secondrecipe.com/wp-content/uploads/2020/07/paneer-tikka-masala.jpg",
                            PRODUCT_EXTRA,
                            "Vegetable",
                            "Indian",
                            "180",
                            TOP_SELLING
                        )
                    )
                    productCategoryTable.child("Palak Paneer").setValue(
                        ProductCategory(
                            "Palak Paneer",
                            "https://www.secondrecipe.com/wp-content/uploads/2020/07/paneer-tikka-masala.jpg",
                            PRODUCT_EXTRA,
                            "Vegetable",
                            "Indian",
                            "160"
                        )
                    )
                    productCategoryTable.child("Paneer Angara").setValue(
                        ProductCategory(
                            "Palak Paneer",
                            "https://www.secondrecipe.com/wp-content/uploads/2020/07/paneer-tikka-masala.jpg",
                            PRODUCT_EXTRA,
                            "Vegetable",
                            "Indian",
                            "170",
                            TOP_SELLING
                        )
                    )
                    productCategoryTable.child("Margarita Pizza").setValue(
                        ProductCategory(
                            "Margarita Pizza",
                            "https://www.takeaway.com/foodwiki/uploads/sites/11/2019/06/italian-cuisine-47-1440x600.jpg",
                            PRODUCT_EXTRA,
                            "Pizza",
                            "Italian",
                            "270",
                            TOP_SELLING
                        )
                    )
                    productCategoryTable.child("Pesto Pizza").setValue(
                        ProductCategory(
                            "Pesto Pizza",
                            "https://www.takeaway.com/foodwiki/uploads/sites/11/2019/06/italian-cuisine-47-1440x600.jpg",
                            PRODUCT_EXTRA,
                            "Pizza",
                            "Italian",
                            "250"
                        )
                    )
                    productCategoryTable.child("Penne").setValue(
                        ProductCategory(
                            "Penne",
                            "https://www.takeaway.com/foodwiki/uploads/sites/11/2019/06/italian-cuisine-47-1440x600.jpg",
                            PRODUCT_EXTRA,
                            "Pasta",
                            "Italian",
                            "285",
                            TOP_SELLING
                        )
                    )
                    productCategoryTable.child("Fusilli").setValue(
                        ProductCategory(
                            "Fusilli",
                            "https://www.takeaway.com/foodwiki/uploads/sites/11/2019/06/italian-cuisine-47-1440x600.jpg",
                            PRODUCT_EXTRA,
                            "Pasta",
                            "Italian",
                            "295"
                        )
                    )
                    productCategoryTable.child("Bosco").setValue(
                        ProductCategory(
                            "Bosco",
                            "https://www.takeaway.com/foodwiki/uploads/sites/11/2019/06/italian-cuisine-47-1440x600.jpg",
                            PRODUCT_EXTRA,
                            "Salad",
                            "Italian",
                            "160"
                        )
                    )
                    productCategoryTable.child("Greek Salad").setValue(
                        ProductCategory(
                            "Greek Salad",
                            "https://www.takeaway.com/foodwiki/uploads/sites/11/2019/06/italian-cuisine-47-1440x600.jpg",
                            PRODUCT_EXTRA,
                            "Salad",
                            "Italian",
                            "140",
                            TOP_SELLING
                        )
                    )
                }

            }
        }
        productCategoryTable.addValueEventListener(valueEventListener)
    }

    private fun addDataToFirebaseDBCategory() {
        val database: FirebaseDatabase = FirebaseDatabase.getInstance()
        val categoryTable: DatabaseReference = database.getReference(CATEGORY_EXTRA)
        val valueEventListener = object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {

            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                //Get User Information


                if (dataSnapshot.hasChildren()) {
                } else {
                    categoryTable.child("Starter").setValue(
                        Category(
                            "Starter",
                            "https://orianofood.online/wp-content/uploads/2020/08/Appetizers-300x300.jpg",
                            PRODUCT_CATEGORY_EXTRA,
                            "Indian"
                        )
                    )

                    categoryTable.child("Indian Breads").setValue(
                        Category(
                            "Indian Breads",
                            "https://www.budgetbytes.com/wp-content/uploads/2010/09/Homemade-Naan-stack-1.jpg",
                            PRODUCT_CATEGORY_EXTRA,
                            "Indian"
                        )
                    )
                    categoryTable.child("Vegetables").setValue(
                        Category(
                            "Vegetables",
                            "https://www.secondrecipe.com/wp-content/uploads/2020/07/paneer-tikka-masala.jpg",
                            PRODUCT_CATEGORY_EXTRA,
                            "Indian"
                        )
                    )


                    /*var productCategory = ProductCategory(
                        "Paneer Tikka",
                        "https://orianofood.online/wp-content/uploads/2020/08/Appetizers-300x300.jpg",
                        PRODUCT_EXTRA,
                        CATEGORY_EXTRA
                    )
                    productCategoryTable.child("Starter").setValue(productCategory)

                    productCategory = ProductCategory(
                        "Naan",
                        "https://www.budgetbytes.com/wp-content/uploads/2010/09/Homemade-Naan-stack-1.jpg",
                        PRODUCT_EXTRA,
                        CATEGORY_EXTRA
                    )
                    productCategoryTable.child("Indian Breads").setValue(productCategory)
                    category = Category(
                        "Onion rings",
                        "https://orianofood.online/wp-content/uploads/2020/08/Appetizers-300x300.jpg"
                    )
                    categoryTable.child("Starter").setValue(category)
                    category = Category(
                        "Rumali Roti",
                        "https://www.budgetbytes.com/wp-content/uploads/2010/09/Homemade-Naan-stack-1.jpg"
                    )
                    categoryTable.child("Indian Breads").setValue(category)
                    category = Category(
                        "Masala Papad",
                        "https://orianofood.online/wp-content/uploads/2020/08/Appetizers-300x300.jpg"
                    )
                    categoryTable.child("Starter").setValue(category)
                    category = Category(
                        "Wheat Roti",
                        "https://www.budgetbytes.com/wp-content/uploads/2010/09/Homemade-Naan-stack-1.jpg"
                    )
                    categoryTable.child("Indian Breads").setValue(category)
                    category = Category(
                        "Paneer Tikka Masala",
                        "https://www.secondrecipe.com/wp-content/uploads/2020/07/paneer-tikka-masala.jpg"
                    )
                    categoryTable.child("Vegetables").setValue(category)
                    category = Category(
                        "Palak Paneer",
                        "https://www.secondrecipe.com/wp-content/uploads/2020/07/paneer-tikka-masala.jpg"
                    )
                    categoryTable.child("Vegetables").setValue(category)*/
                    categoryTable.child("Pasta").setValue(
                        Category(
                            "Pasta",
                            "https://www.takeaway.com/foodwiki/uploads/sites/11/2019/06/italian-cuisine-47-1440x600.jpg",
                            PRODUCT_CATEGORY_EXTRA,
                            "Italian"
                        )
                    )
                    categoryTable.child("Pizza").setValue(
                        Category(
                            "Pizza",
                            "https://www.takeaway.com/foodwiki/uploads/sites/11/2019/06/italian-cuisine-47-1440x600.jpg",
                            PRODUCT_CATEGORY_EXTRA,
                            "Italian"
                        )
                    )
                    categoryTable.child("Salad").setValue(
                        Category(
                            "Salad",
                            "https://www.takeaway.com/foodwiki/uploads/sites/11/2019/06/italian-cuisine-47-1440x600.jpg",
                            PRODUCT_CATEGORY_EXTRA,
                            "Italian"
                        )
                    )
                    /*category = Category(
                        "Salad",
                        "https://www.takeaway.com/foodwiki/uploads/sites/11/2019/06/italian-cuisine-47-1440x600.jpg"
                    )
                    categoryTable.child("Italian").setValue(category)
                    category = Category(
                        "Shahi Paneer",
                        "https://www.secondrecipe.com/wp-content/uploads/2020/07/paneer-tikka-masala.jpg"
                    )
                    categoryTable.child("Vegetables").setValue(category)
                    category = Category(
                        "Pizza",
                        "https://www.takeaway.com/foodwiki/uploads/sites/11/2019/06/italian-cuisine-47-1440x600.jpg"
                    )
                    categoryTable.child("Italian").setValue(category)
                    category = Category(
                        "Margarita Pizza",
                        "https://www.takeaway.com/foodwiki/uploads/sites/11/2019/06/italian-cuisine-47-1440x600.jpg"
                    )
                    categoryTable.child("Pizza").setValue(category)
                    category = Category(
                        "Pesto Pizza",
                        "https://www.takeaway.com/foodwiki/uploads/sites/11/2019/06/italian-cuisine-47-1440x600.jpg"
                    )
                    categoryTable.child("Pizza").setValue(category)
                    category = Category(
                        "Penne",
                        "https://www.takeaway.com/foodwiki/uploads/sites/11/2019/06/italian-cuisine-47-1440x600.jpg"
                    )
                    categoryTable.child("Pasta").setValue(category)
                    category = Category(
                        "Fusilli",
                        "https://www.takeaway.com/foodwiki/uploads/sites/11/2019/06/italian-cuisine-47-1440x600.jpg"
                    )
                    categoryTable.child("Pasta").setValue(category)
                    category = Category(
                        "Bosco",
                        "https://www.takeaway.com/foodwiki/uploads/sites/11/2019/06/italian-cuisine-47-1440x600.jpg"
                    )
                    categoryTable.child("Salad").setValue(category)
                    category = Category(
                        "Greek Salad",
                        "https://www.takeaway.com/foodwiki/uploads/sites/11/2019/06/italian-cuisine-47-1440x600.jpg"
                    )
                    categoryTable.child("Salad").setValue(category)*/
                }

            }
        }
        categoryTable.addValueEventListener(valueEventListener)
    }

    private fun addDataToFirebaseDBMenu() {
        val database: FirebaseDatabase = FirebaseDatabase.getInstance()
        val menuTable: DatabaseReference = database.getReference(MENU_EXTRA)

        val valueEventListener = object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {

            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                //Get User Information


                if (dataSnapshot.hasChildren()) {
                } else {

                    menuTable.child("Indian").setValue(
                        Menu(
                            "Indian",
                            "https://www.secondrecipe.com/wp-content/uploads/2020/07/paneer-tikka-masala.jpg",
                            CATEGORY_EXTRA
                        )
                    )

                    menuTable.child("Italian").setValue(
                        Menu(
                            "Italian",
                            "https://www.takeaway.com/foodwiki/uploads/sites/11/2019/06/italian-cuisine-47-1440x600.jpg",
                            CATEGORY_EXTRA
                        )
                    )

                    menuTable.child("Deserts").setValue(
                        Menu(
                            "Deserts",
                            null,
                            CATEGORY_EXTRA
                        )
                    )

                    menuTable.child("Drinks").setValue(
                        Menu(
                            "Drinks",
                            null,
                            CATEGORY_EXTRA
                        )
                    )

                }

            }
        }
        menuTable.addValueEventListener(valueEventListener)
    }

    private fun loadTopSellingItems() {
        val productQuery = products.orderByKey()
        val productsOption = FirebaseRecyclerOptions.Builder<ProductCategory>()
            .setQuery(products, ProductCategory::class.java).build()

        productViewHolder = object : FirebaseRecyclerAdapter<ProductCategory, ProductViewHolder>(
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
                        val intent = Intent(this@HomeActivity, ProductActivity::class.java)
                        intent.putExtra(
                            PRODUCT_CATEGORY_EXTRA,
                            productViewHolder.getRef(position).key
                        )
                        startActivity(intent)
                    }
                }
                holder.setitemClickListener(itemClickListener)
            }
        }
        topSellingRecyclerview.adapter = productViewHolder
    }

    private fun loadMenuItems() {
        val menuQuery = menu.orderByKey()
        val menuOption = FirebaseRecyclerOptions.Builder<Menu>()
            .setQuery(menu, Menu::class.java).build()

        viewHolder = object : FirebaseRecyclerAdapter<Menu, MenuViewHolder>(menuOption) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.category_card_view, parent, false)

                val name = view.findViewById<TextView>(R.id.categoryName)
                val img = view.findViewById<ImageView>(R.id.categoryImage)
                return MenuViewHolder(view, img, name)
            }

            override fun onBindViewHolder(holder: MenuViewHolder, position: Int, model: Menu) {
                holder.categoryName.text = model.name

                Picasso.get()
                    .load(model.image)
                    .placeholder(R.mipmap.bg_home)
                    .error(R.mipmap.bg_home)
                    .into(holder.categoryImg)

                val itemClickListener = object : ItemClickListener {
                    override fun onClick(view: View, position: Int, isLongClick: Boolean) {

                        val intent = Intent(this@HomeActivity, CategoryActivity::class.java)
                        //intent.putExtra(CATEGORY_EXTRA, viewHolder.getRef(position).key)
                        intent.putExtra(CATEGORY_EXTRA, holder.categoryName.text)
                        startActivity(intent)
                    }
                }
                holder.setitemClickListener(itemClickListener)
            }
        }
        recyclerview.adapter = viewHolder
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
        viewHolder.startListening()
        productViewHolder.startListening()
    }

    override fun onPause() {
        super.onPause()
        viewHolder.stopListening()
        productViewHolder.stopListening()
    }

    override fun onStart() {
        super.onStart()
        viewHolder.startListening()
        productViewHolder.startListening()
    }

    override fun onStop() {
        super.onStop()
        viewHolder.stopListening()
        productViewHolder.stopListening()
    }

    fun Any.toast(context: Context, duration: Int = Toast.LENGTH_SHORT): Toast {
        return Toast.makeText(context, this.toString(), duration).apply { show() }
    }


}
