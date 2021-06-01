package com.atlas.orianofood.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.atlas.orianofood.R
import com.atlas.orianofood.model.User
import com.atlas.orianofood.utils.Common
import com.google.firebase.database.*
import kotlinx.android.synthetic.orderOnline.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val database: FirebaseDatabase = FirebaseDatabase.getInstance()
        val userTable: DatabaseReference = database.getReference("User")

        progressBar.visibility = View.GONE

        btn_signIn.setOnClickListener {

            setLayoutVisibility(View.VISIBLE, View.GONE)

            val valueEventListener = object : ValueEventListener {
                override fun onCancelled(databaseError: DatabaseError) {

                }

                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    //Get User Information

                    setLayoutVisibility(View.GONE, View.VISIBLE)

                    if (dataSnapshot.child(et_phone_number.text.toString()).exists()) {


                        val user: User? =
                            dataSnapshot.child(et_phone_number.text.toString()).getValue(
                                User::class.java
                            )
                        user?.phone = et_phone_number.text.toString()
                        if (user != null) {

                            if (user.password == et_password.text.toString()) {
                                "Login Successful.".toast(this@LoginActivity)
                                var intent = Intent(this@LoginActivity, HomeActivity::class.java)

                                /*when {
                                    user.name.isNullOrEmpty() -> {
                                        intent =
                                            Intent(this@LoginActivity, ProfileActivity::class.java)
                                    }
                                    user.phone.isNullOrEmpty() -> {
                                        intent =
                                            Intent(this@LoginActivity, ProfileActivity::class.java)
                                    }
                                    (DatabaseHandler(this@LoginActivity).getDefaultAddress() == "No Default Address Available") -> {
                                        intent = Intent(
                                            this@LoginActivity,
                                            AddAddressActivity::class.java
                                        )
                                    }

                                }*/

                                intent =
                                    Intent(this@LoginActivity, HomeActivity::class.java)
                                Common.currentUser = user
                                startActivity(intent)
                                finish()

                            } else {
                                "Wrong Password.".toast(this@LoginActivity)
                            }
                        }
                    } else {
                        "User does not exist.".toast(this@LoginActivity)
                    }
                }
            }
            userTable.addValueEventListener(valueEventListener)
        }
    }


    fun setLayoutVisibility(progressBarVisibility: Int, otherVisibility: Int) {
        progressBar.visibility = progressBarVisibility
        et_password.visibility = otherVisibility
        et_phone_number.visibility = otherVisibility
        btn_signIn.visibility = otherVisibility
    }

    fun Any.toast(context: Context, duration: Int = Toast.LENGTH_SHORT): Toast {
        return Toast.makeText(context, this.toString(), duration).apply { show() }
    }

    fun intentSignUp(view: View) {
        startActivity(Intent(this, RegisterActivity::class.java))
        finish()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, SplashActivity::class.java))
        finish()
    }


}