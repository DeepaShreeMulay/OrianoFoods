package com.atlas.orianofood.activity

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.viewpager.widget.ViewPager
import com.atlas.orianofood.R
import com.atlas.orianofood.activity.ui.main.SectionsPagerAdapter
import com.atlas.orianofood.adapter.GalleryViewHolder
import com.atlas.orianofood.interfaces.ItemClickListener
import com.atlas.orianofood.model.Gallery
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.orderOnline.content_home.*


class GalleryActivity : AppCompatActivity() {
    lateinit var database: FirebaseDatabase
    lateinit var menu: DatabaseReference
    lateinit var viewHolder: FirebaseRecyclerAdapter<Gallery, GalleryViewHolder>

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
                        com.atlas.orianofood.model.Gallery(
                            "Indian",
                            "https://www.secondrecipe.com/wp-content/uploads/2020/07/paneer-tikka-masala.jpg",
                            "Food"
                        )
                    )

                    menuTable.child("Italian").setValue(
                        com.atlas.orianofood.model.Gallery(
                            "Italian",
                            "https://www.takeaway.com/foodwiki/uploads/sites/11/2019/06/italian-cuisine-47-1440x600.jpg",
                            "Event"
                        )
                    )

                    menuTable.child("Deserts").setValue(
                        com.atlas.orianofood.model.Gallery(
                            "Deserts",
                            null,
                            "Food"
                        )
                    )

                    menuTable.child("Drinks").setValue(
                        com.atlas.orianofood.model.Gallery(
                            "Drinks",
                            null,
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
                    .inflate(R.layout.category_cardview_item, parent, false)

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
                    .placeholder(R.mipmap.bg_home)
                    .error(R.mipmap.bg_home)
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
        /*if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {*/
        super.onBackPressed()
        val intent = Intent(this@GalleryActivity, HomeActivity::class.java)
        startActivity(intent)
        finish()
        // }
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
}