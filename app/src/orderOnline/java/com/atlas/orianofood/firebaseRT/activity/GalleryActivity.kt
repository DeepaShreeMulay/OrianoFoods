package com.atlas.orianofood.firebaseRT.activity

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.viewpager.widget.ViewPager
import com.atlas.orianofood.R
import com.atlas.orianofood.firebaseRT.activity.ui.main.SectionsPagerAdapter
import com.atlas.orianofood.firebaseRT.adapter.GalleryViewHolder
import com.atlas.orianofood.firebaseRT.interfaces.ItemClickListener
import com.atlas.orianofood.firebaseRT.model.Gallery
import com.atlas.orianofood.mvvm.activity.HomeSPActivity
import com.atlas.orianofood.mvvm.utils.logout
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.orderOnline.activity_home.*
import kotlinx.android.synthetic.orderOnline.app_bar_home.*
import kotlinx.android.synthetic.orderOnline.content_home.*
import kotlinx.android.synthetic.orderOnline.nav_header_home.view.*


class GalleryActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    lateinit var database: FirebaseDatabase
    lateinit var menu: DatabaseReference
    lateinit var viewHolder: FirebaseRecyclerAdapter<Gallery, GalleryViewHolder>

    private val currentUser = FirebaseAuth.getInstance().currentUser
    private val activity = this@GalleryActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)
        val fab: FloatingActionButton = findViewById(R.id.fab)

        addDataToFirebaseDBMenu()

        database = FirebaseDatabase.getInstance()
        menu = database.getReference("Gallery")

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

        val manager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        recyclerview.layoutManager = manager
        recyclerview.setHasFixedSize(true)
        loadMenuItems()
    }

    private fun addDataToFirebaseDBMenu() {
        val database: FirebaseDatabase = FirebaseDatabase.getInstance()
        val menuTable: DatabaseReference = database.getReference("Gallery")

        val valueEventListener = object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {

            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                //Get User Information


                if (dataSnapshot.hasChildren()) {
                } else {

                    menuTable.child("Indian").setValue(
                        com.atlas.orianofood.firebaseRT.model.Gallery(
                            "Indian",
                            "https://orianofood.online/wp-content/uploads/2020/09/crespelle-primavera-370x370.jpeg",
                            "Food"
                        )
                    )

                    menuTable.child("Italian").setValue(
                        com.atlas.orianofood.firebaseRT.model.Gallery(
                            "Italian",
                            "https://orianofood.online/wp-content/uploads/2020/09/TORTELLINI-ZARBA-370x370.jpg",
                            "Event"
                        )
                    )

                    menuTable.child("Deserts").setValue(
                        com.atlas.orianofood.firebaseRT.model.Gallery(
                            "Deserts",
                            "https://orianofood.online/wp-content/uploads/2020/09/olive_Cheesecakes-f005ffb-150x150.jpg",
                            "Food"
                        )
                    )

                    menuTable.child("Drinks").setValue(
                        com.atlas.orianofood.firebaseRT.model.Gallery(
                            "Drinks",
                            "https://orianofood.online/wp-content/uploads/2020/08/Beer-1-300x300.jpg",
                            "Ambiance"
                        )
                    )

                    menuTable.child("Indian1").setValue(
                        com.atlas.orianofood.firebaseRT.model.Gallery(
                            "Indian1",
                            "https://orianofood.online/wp-content/uploads/2020/09/crespelle-primavera-150x150.jpeg",
                            "Food"
                        )
                    )

                    menuTable.child("Italian1").setValue(
                        com.atlas.orianofood.firebaseRT.model.Gallery(
                            "Italian1",
                            "https://orianofood.online/wp-content/uploads/2020/08/FreshSalads-1-300x300.jpg",
                            "Event"
                        )
                    )

                    menuTable.child("Deserts1").setValue(
                        com.atlas.orianofood.firebaseRT.model.Gallery(
                            "Deserts1",
                            "https://orianofood.online/wp-content/uploads/2020/09/FRESH-FRUIT-AND-CREAM-CAKE-370x370.jpg",
                            "Food"
                        )
                    )

                    menuTable.child("Drinks1").setValue(
                        com.atlas.orianofood.firebaseRT.model.Gallery(
                            "Drinks1",
                            "https://www.budgetbytes.com/wp-content/uploads/2010/09/Homemade-Naan-stack-1.jpg",
                            "Ambiance"
                        )
                    )

                }

            }
        }
        menuTable.addValueEventListener(valueEventListener)
    }

    private fun loadMenuItems() {
        val menuQuery = menu.orderByKey()
        val menuOption = FirebaseRecyclerOptions.Builder<Gallery>()
            .setQuery(menu, Gallery::class.java).build()

        viewHolder = object : FirebaseRecyclerAdapter<Gallery, GalleryViewHolder>(menuOption) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.staggered_cardview_item, parent, false)

                val name = view.findViewById<TextView>(R.id.categoryName)
                val img = view.findViewById<ImageView>(R.id.categoryImage)
                return GalleryViewHolder(view, img, name)
            }

            override fun onBindViewHolder(
                holder: GalleryViewHolder,
                position: Int,
                model: Gallery
            ) {
                holder.categoryName.text = model.name

                Picasso.get()
                    .load(model.image)
                    .placeholder(R.drawable.ic_menu_gallery)
                    .error(R.drawable.ic_menu_gallery)
                    .into(holder.categoryImg)

                val itemClickListener = object : ItemClickListener {
                    override fun onClick(view: View, position: Int, isLongClick: Boolean) {

                        /*val intent = Intent(this@GalleryActivity, CategoryActivity::class.java)
                        //intent.putExtra(CATEGORY_EXTRA, viewHolder.getRef(position).key)
                        intent.putExtra(CATEGORY_EXTRA, holder.categoryName.text)
                        startActivity(intent)
                        finish()*/
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
            val intent = Intent(this@GalleryActivity, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onResume() {
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
            R.id.nav_log_out -> {
                // Handle the camera action
                logout()
            }

        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}