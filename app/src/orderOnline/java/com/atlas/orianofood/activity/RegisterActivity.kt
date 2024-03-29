package com.atlas.orianofood.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.atlas.orianofood.R
import com.atlas.orianofood.model.User
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
                            et_signUp_phone_number.text.toString(), ""
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
