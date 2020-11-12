package com.atlas.orianofood.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.atlas.orianofood.R
import com.atlas.orianofood.model.Category
import com.atlas.orianofood.model.User
import com.atlas.orianofood.utils.CATEGORY_EXTRA
import com.google.firebase.database.*
import kotlinx.android.synthetic.orderOnline.activity_register.*

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val database: FirebaseDatabase = FirebaseDatabase.getInstance()
        val userTable: DatabaseReference = database.getReference("User")
        progressBarSignUp.visibility = View.INVISIBLE

        btn_signUp.setOnClickListener {
            setLayoutVisibility(View.VISIBLE, View.INVISIBLE)


            val valueEventListener = object : ValueEventListener {
                override fun onCancelled(databaseError: DatabaseError) {

                }

                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    //Get User Information

                    setLayoutVisibility(View.INVISIBLE, View.VISIBLE)

                    if (dataSnapshot.child(et_signUp_phone_number.text.toString()).exists()) {
                        "This phone number already registered.".toast(this@RegisterActivity)
                    } else {
                        val user = User(
                            et_signUp_name.text.toString(), et_signUp_password.text.toString(),
                            et_signUp_phone_number.text.toString()
                        )
                        userTable.child(et_signUp_phone_number.text.toString()).setValue(user)
                        "User registered successful.".toast(this@RegisterActivity)
                        finish()
                    }

                }
            }
            userTable.addValueEventListener(valueEventListener)
        }
    }

    fun addDataToFirebaseDB() {
        val database: FirebaseDatabase = FirebaseDatabase.getInstance()
        val categoryTable: DatabaseReference = database.getReference(CATEGORY_EXTRA)

        val valueEventListener = object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {

            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                //Get User Information

                setLayoutVisibility(View.INVISIBLE, View.VISIBLE)

                if (dataSnapshot.hasChildren()) {
                } else {
                    var category = Category(
                        "Starter",
                        "https://orianofood.online/wp-content/uploads/2020/08/Appetizers-300x300.jpg"
                    )
                    categoryTable.child("Indian").setValue(category)
                    category = Category(
                        "Indian Breads",
                        "https://www.budgetbytes.com/wp-content/uploads/2010/09/Homemade-Naan-stack-1.jpg"
                    )
                    categoryTable.child("Indian").setValue(category)
                    category = Category(
                        "Vegetables",
                        "https://www.secondrecipe.com/wp-content/uploads/2020/07/paneer-tikka-masala.jpg"
                    )
                    categoryTable.child("Indian").setValue(category)
                    category = Category(
                        "Indian",
                        "https://www.secondrecipe.com/wp-content/uploads/2020/07/paneer-tikka-masala.jpg"
                    )
                    categoryTable.child("Menu").setValue(category)
                    category = Category(
                        "Italian",
                        "https://www.takeaway.com/foodwiki/uploads/sites/11/2019/06/italian-cuisine-47-1440x600.jpg"
                    )
                    categoryTable.child("Menu").setValue(category)
                    category = Category(
                        "Paneer Tikka",
                        "https://orianofood.online/wp-content/uploads/2020/08/Appetizers-300x300.jpg"
                    )
                    categoryTable.child("Starter").setValue(category)
                    category = Category(
                        "Naan",
                        "https://www.budgetbytes.com/wp-content/uploads/2010/09/Homemade-Naan-stack-1.jpg"
                    )
                    categoryTable.child("Indian Breads").setValue(category)
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
                    categoryTable.child("Vegetables").setValue(category)
                    category = Category(
                        "Pasta",
                        "https://www.takeaway.com/foodwiki/uploads/sites/11/2019/06/italian-cuisine-47-1440x600.jpg"
                    )
                    categoryTable.child("Italian").setValue(category)
                    category = Category(
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
                    categoryTable.child("Salad").setValue(category)
                }

            }
        }
        categoryTable.addValueEventListener(valueEventListener)
    }

    fun setLayoutVisibility(progressBarVisibility: Int, otherVisibility: Int) {

        progressBarSignUp.visibility = progressBarVisibility
        et_signUp_name.visibility = otherVisibility
        et_signUp_phone_number.visibility = otherVisibility
        et_signUp_password.visibility = otherVisibility
        btn_signUp.visibility = otherVisibility
    }

    fun intentSignIn(view: View) {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    fun Any.toast(context: Context, duration: Int = Toast.LENGTH_SHORT): Toast {
        return Toast.makeText(context, this.toString(), duration).apply { show() }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, SplashActivity::class.java))
        finish()
    }

}
