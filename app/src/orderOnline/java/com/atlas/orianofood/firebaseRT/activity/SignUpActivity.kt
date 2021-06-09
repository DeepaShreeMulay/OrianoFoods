package com.atlas.orianofood.firebaseRT.activity

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.atlas.orianofood.R
import com.atlas.orianofood.firebaseRT.utils.login
import com.atlas.orianofood.firebaseRT.utils.toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.orderOnline.activity_signup.*

class SignUpActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    val activity = this@SignUpActivity


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        mAuth = FirebaseAuth.getInstance()

        btn_signUp.setOnClickListener {
            val email = et_signUp_email.text.toString().trim()
            val password = et_signUp_password.text.toString().trim()

            if (email.isEmpty()) {
                et_signUp_email.error = "Email Required"
                et_signUp_email.requestFocus()
                return@setOnClickListener
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                et_signUp_email.error = "Valid Email Required"
                et_signUp_email.requestFocus()
                return@setOnClickListener
            }

            if (password.isEmpty() || password.length < 6) {
                et_signUp_password.error = "Minimum 6 char password required"
                et_signUp_password.requestFocus()
                return@setOnClickListener
            }

            registerUser(email, password)

        }

        et_signUP.setOnClickListener {
            startActivity(Intent(activity, SignInActivity::class.java))
        }
    }

    private fun registerUser(email: String, password: String) {
        progressBarSignUp.visibility = View.VISIBLE
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                progressBarSignUp.visibility = View.GONE
                if (task.isSuccessful) {
                    login()
                } else {
                    task.exception?.message?.let {
                        toast(it)
                    }
                }
            }

    }

    override fun onStart() {
        super.onStart()
        mAuth.currentUser?.let {
            login()
        }
    }
}