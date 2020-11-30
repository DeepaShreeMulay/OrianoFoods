package com.atlas.orianofood.activity

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
import com.atlas.orianofood.adapter.MenuViewHolder
import com.atlas.orianofood.adapter.OffersViewHolder
import com.atlas.orianofood.adapter.ProductViewHolder
import com.atlas.orianofood.database.DatabaseHandler
import com.atlas.orianofood.interfaces.ItemClickListener
import com.atlas.orianofood.model.Category
import com.atlas.orianofood.model.Menu
import com.atlas.orianofood.model.Offer
import com.atlas.orianofood.model.ProductCategory
import com.atlas.orianofood.utils.*
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.orderOnline.activity_home.*
import kotlinx.android.synthetic.orderOnline.app_bar_home.*
import kotlinx.android.synthetic.orderOnline.content_home.*
import kotlinx.android.synthetic.orderOnline.nav_header_home.view.*
import java.text.NumberFormat
import java.util.*

class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var database: FirebaseDatabase
    lateinit var menu: DatabaseReference
    lateinit var offer: DatabaseReference
    lateinit var topSelling: DatabaseReference
    lateinit var topRated: DatabaseReference
    lateinit var viewHolder: FirebaseRecyclerAdapter<Menu, MenuViewHolder>
    lateinit var topRatedViewHolder: FirebaseRecyclerAdapter<ProductCategory, ProductViewHolder>
    lateinit var topSellingViewHolder: FirebaseRecyclerAdapter<ProductCategory, ProductViewHolder>
    lateinit var offersViewHolder: FirebaseRecyclerAdapter<Offer, OffersViewHolder>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        toolbar.title = ""
        setSupportActionBar(toolbar)

        addDataToFirebaseDBAds()
        addDataToFirebaseDBMenu()
        addDataToFirebaseDBCategory()
        addDataToFirebaseDBProductCategory()

        //firebase init
        val databaseHandler: DatabaseHandler = DatabaseHandler(this)
        database = FirebaseDatabase.getInstance()
        menu = database.getReference(MENU_EXTRA)
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
        if (Common.currentUser != null) {
            view.userLogged.text = Common.currentUser!!.name
        } else {
            view.userLogged.text = FirebaseAuth.getInstance().currentUser?.phoneNumber
                ?: FirebaseAuth.getInstance().currentUser?.email
        }

        val manager = GridLayoutManager(this, 3)
        recyclerview.layoutManager = manager
        recyclerview.setHasFixedSize(true)
        loadMenuItems()

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
                            "150",
                            "yes",
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
                            "50",
                            "60"
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
                            "80",
                            "yes",
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
                            "45",
                            "50"
                        )
                    )
                    productCategoryTable.child("Naan").setValue(
                        ProductCategory(
                            "Naan",
                            "https://www.budgetbytes.com/wp-content/uploads/2010/09/Homemade-Naan-stack-1.jpg",
                            PRODUCT_EXTRA,
                            "Indian Breads",
                            "Indian",
                            "45",
                            "50"
                        )
                    )
                    productCategoryTable.child("Wheat Roti").setValue(
                        ProductCategory(
                            "Wheat Roti",
                            "https://www.budgetbytes.com/wp-content/uploads/2010/09/Homemade-Naan-stack-1.jpg",
                            PRODUCT_EXTRA,
                            "Indian Breads",
                            "Indian",
                            "20",
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
                            "190",
                            "yes",
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
                            "160",
                            "175"
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
                            "180",
                            "yes",
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
                            "280",
                            "yes",
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
                            "250",
                            "260"
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
                            "300",
                            "yes",
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
                            "295",
                            "300"
                        )
                    )
                    productCategoryTable.child("Bosco").setValue(
                        ProductCategory(
                            "Bosco",
                            "https://www.takeaway.com/foodwiki/uploads/sites/11/2019/06/italian-cuisine-47-1440x600.jpg",
                            PRODUCT_EXTRA,
                            "Salad",
                            "Italian",
                            "160",
                            "170"
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
                            "180",
                            "yes",
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
                            "https://orianofood.online/wp-content/uploads/2020/09/CHOCOLATE-BROWNIES-WITH-HOT-CHOCOLATE-SAUCE-AND-ICE-CREAM-150x150.jpg",
                            CATEGORY_EXTRA
                        )
                    )

                    menuTable.child("Drinks").setValue(
                        Menu(
                            "Drinks",
                            "https://orianofood.online/wp-content/uploads/2020/09/tc-chamapagne-brands-1-1544808409-150x150.jpg",
                            CATEGORY_EXTRA
                        )
                    )

                    menuTable.child("Imported Wines").setValue(
                        Menu(
                            "Imported Wines",
                            "https://orianofood.online/wp-content/uploads/2020/09/Imported-Wines..jpg",
                            CATEGORY_EXTRA
                        )
                    )

                    menuTable.child("Soups").setValue(
                        Menu(
                            "Soups",
                            "https://orianofood.online/wp-content/uploads/2020/08/soup-150x150.jpg",
                            CATEGORY_EXTRA
                        )
                    )

                    menuTable.child("Main Course").setValue(
                        Menu(
                            "Main Course",
                            "https://orianofood.online/wp-content/uploads/2020/08/sliced-vegetable-and-cooked-food-on-white-ceramic-plate-1234535-150x150.jpg",
                            CATEGORY_EXTRA
                        )
                    )

                    menuTable.child("Cakes").setValue(
                        Menu(
                            "Cakes",
                            "https://orianofood.online/wp-content/uploads/2020/09/FRESH-FRUIT-AND-CREAM-CAKE-150x150.jpg",
                            CATEGORY_EXTRA
                        )
                    )

                    menuTable.child("Juice").setValue(
                        Menu(
                            "Juice",
                            "https://orianofood.online/wp-content/uploads/2020/09/STRAWBERRY-1-150x150.jpg",
                            CATEGORY_EXTRA
                        )
                    )

                    menuTable.child("Coffee").setValue(
                        Menu(
                            "Coffee",
                            "https://orianofood.online/wp-content/uploads/2020/09/coffee.jpg",
                            CATEGORY_EXTRA
                        )
                    )

                }

            }
        }
        menuTable.addValueEventListener(valueEventListener)
    }
    private fun addDataToFirebaseDBAds() {
        val database: FirebaseDatabase = FirebaseDatabase.getInstance()
        val menuTable: DatabaseReference = database.getReference("Offers")

        val valueEventListener = object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {

            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                //Get User Information


                if (dataSnapshot.hasChildren()) {
                } else {

                    menuTable.child("Ad1").setValue(
                        Offer(
                            "Ad1",
                            "https://orianofood.online/wp-content/uploads/2020/09/ad1-1.jpg"
                        )
                    )

                    menuTable.child("Ad2").setValue(
                        Offer(
                            "Ad2",
                            "https://orianofood.online/wp-content/uploads/2020/09/ad2-1.jpg"
                        )
                    )

                }

            }
        }
        menuTable.addValueEventListener(valueEventListener)
    }

    private fun loadTopSellingItems() {
        val productQuery = topSelling.orderByChild("offer").equalTo("Top Selling")
        val productsOption = FirebaseRecyclerOptions.Builder<ProductCategory>()
            .setQuery(productQuery, ProductCategory::class.java).build()

        topSellingViewHolder = object : FirebaseRecyclerAdapter<ProductCategory, ProductViewHolder>(
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
                    .placeholder(R.drawable.ic_menu_gallery)
                    .error(R.drawable.ic_menu_gallery)
                    .into(holder.img)

                val itemClickListener = object : ItemClickListener {
                    override fun onClick(view: View, position: Int, isLongClick: Boolean) {
                        val intent = Intent(this@HomeActivity, ProductActivity::class.java)
                        intent.putExtra(
                            PRODUCT_CATEGORY_EXTRA,
                            topSellingViewHolder.getRef(position).key
                        )
                        startActivity(intent)
                        finish()
                    }
                }
                holder.setitemClickListener(itemClickListener)
            }
        }
        topSellingRecyclerview.adapter = topSellingViewHolder
    }
    private fun loadTopRatingItems() {
        val productQuery = topRated.orderByKey()
        val productsOption = FirebaseRecyclerOptions.Builder<ProductCategory>()
            .setQuery(topRated, ProductCategory::class.java).build()

        topRatedViewHolder = object : FirebaseRecyclerAdapter<ProductCategory, ProductViewHolder>(
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
                    .placeholder(R.drawable.ic_menu_gallery)
                    .error(R.drawable.ic_menu_gallery)
                    .into(holder.img)

                val itemClickListener = object : ItemClickListener {
                    override fun onClick(view: View, position: Int, isLongClick: Boolean) {
                        val intent = Intent(this@HomeActivity, ProductActivity::class.java)
                        intent.putExtra(
                            PRODUCT_CATEGORY_EXTRA,
                            topRatedViewHolder.getRef(position).key
                        )
                        startActivity(intent)
                        finish()
                    }
                }
                holder.setitemClickListener(itemClickListener)
            }
        }
        topRatingRecyclerview.adapter = topRatedViewHolder
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
                    .placeholder(R.drawable.ic_menu_gallery)
                    .error(R.drawable.ic_menu_gallery)
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
    private fun loadMenuItems() {
        val menuQuery = menu.orderByKey()
        val menuOption = FirebaseRecyclerOptions.Builder<Menu>()
            .setQuery(menu, Menu::class.java).build()

        viewHolder = object : FirebaseRecyclerAdapter<Menu, MenuViewHolder>(menuOption) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.category_cardview_item, parent, false)

                val name = view.findViewById<TextView>(R.id.categoryName)
                val img = view.findViewById<ImageView>(R.id.categoryImage)
                return MenuViewHolder(view, img, name)
            }

            override fun onBindViewHolder(holder: MenuViewHolder, position: Int, model: Menu) {
                holder.categoryName.text = model.name

                Picasso.get()
                    .load(model.image)
                    .resize(300, 300)
                    .placeholder(R.drawable.ic_menu_gallery)
                    .error(R.drawable.ic_menu_gallery)
                    .into(holder.categoryImg)

                val itemClickListener = object : ItemClickListener {
                    override fun onClick(view: View, position: Int, isLongClick: Boolean) {

                        val intent = Intent(this@HomeActivity, CategoryActivity::class.java)
                        //intent.putExtra(CATEGORY_EXTRA, viewHolder.getRef(position).key)
                        intent.putExtra(CATEGORY_EXTRA, holder.categoryName.text as String?)
                        startActivity(intent)
                        finish()
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
            R.id.nav_gallery -> {
                // Handle the camera action
                val intent = Intent(this@HomeActivity, GalleryActivity::class.java)
                startActivity(intent)
                finish()
            }
            R.id.nav_orders -> {
                // Handle the camera action
            }
            R.id.nav_log_out -> {
                // Handle the camera action
                AuthUI.getInstance().signOut(this)
            }

        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onResume() {
        super.onResume()
        loadMenuItems()
        loadTopSellingItems()
        loadTopRatingItems()
        loadOffers()
        viewHolder.startListening()
        topRatedViewHolder.startListening()
        topSellingViewHolder.startListening()
        offersViewHolder.startListening()
    }

    override fun onPause() {
        super.onPause()
        viewHolder.stopListening()
        topSellingViewHolder.stopListening()
        topRatedViewHolder.stopListening()
        offersViewHolder.stopListening()
    }

    override fun onStart() {
        super.onStart()
        viewHolder.startListening()
        topRatedViewHolder.startListening()
        topSellingViewHolder.startListening()
        offersViewHolder.startListening()
    }

    override fun onStop() {
        super.onStop()
        viewHolder.stopListening()
        topRatedViewHolder.stopListening()
        topSellingViewHolder.stopListening()
        offersViewHolder.stopListening()
    }

    fun Any.toast(context: Context, duration: Int = Toast.LENGTH_SHORT): Toast {
        return Toast.makeText(context, this.toString(), duration).apply { show() }
    }


}
