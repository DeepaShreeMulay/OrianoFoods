package com.atlas.orianofood.firebaseRT.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.atlas.orianofood.R
import com.atlas.orianofood.firebaseRT.database.DatabaseHandler
import com.atlas.orianofood.firebaseRT.model.Cart
import com.atlas.orianofood.firebaseRT.model.ProductCategory
import com.atlas.orianofood.firebaseRT.utils.PRODUCT_CATEGORY_EXTRA
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.orderOnline.activity_product_detail.*
import java.text.NumberFormat
import java.util.*

class ProductActivity : AppCompatActivity() {

    lateinit var productId: String
    lateinit var database: FirebaseDatabase
    lateinit var productRef: DatabaseReference
    lateinit var product: ProductCategory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        //firebase init
        database = FirebaseDatabase.getInstance()
        productRef = database.getReference(PRODUCT_CATEGORY_EXTRA)

        productId = intent.getStringExtra(PRODUCT_CATEGORY_EXTRA).toString()

        loadDetailProduct(productId)
        saveToCart()
        go_to_cart.setOnClickListener {
            val intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun loadDetailProduct(productId: String) {
        val valueEventListener = object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {

            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {

                product = dataSnapshot.getValue(ProductCategory::class.java)!!

                Picasso.get()
                    .load(product.image)
                    .resize(300, 300)
                    .placeholder(R.drawable.ic_dish)
                    .error(R.drawable.ic_dish)
                    .into(product_img)

                collapsing.title = product.name

                product_name.text = product.name

                val locale = Locale("en", "IN")
                val nf = NumberFormat.getCurrencyInstance(locale)

                product_price.text = nf.format(product.rate?.toDouble())
                product_description.text =
                    "Descriptive writing may not be your strongest suit but we are confident that we’ll be able to help you write description of sweets and other food products that will make your reader’s mouth water. Our product description writers are highly skilled in painting a picture through words which is exactly what you will need to make your food products stand out among the crowd. With our help, you’ll be able to grab the attention of your readers and have them talk about your product in no time."

            }

        }
        productRef.child(productId).addValueEventListener(valueEventListener)
    }

    private fun saveToCart() {
        btn_add_cart.setOnClickListener {
            // Database(this)
            DatabaseHandler(this).createOrderTable()
            DatabaseHandler(this)
                .getCartItem(
                    Cart(
                        productId,
                        product.name!!,
                        btn_number.number,
                        product.rate!!,
                        "0"
                    )
                )

            "${product.name} added to cart".toast(this)
        }
    }

    fun Any.toast(context: Context, duration: Int = Toast.LENGTH_SHORT): Toast {
        return Toast.makeText(context, this.toString(), duration).apply { show() }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this@ProductActivity, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

}
