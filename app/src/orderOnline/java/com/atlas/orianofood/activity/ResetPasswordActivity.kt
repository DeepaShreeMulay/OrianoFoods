package com.atlas.orianofood.activity

import android.os.Bundle
import android.util.Patterns
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.atlas.orianofood.R
import com.atlas.orianofood.utils.toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.orderOnline.activity_reset_password.*

class ResetPasswordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)

        button_reset_password.setOnClickListener {
            val email = et_signUp_email.text.toString().trim()

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

            progressBar.visibility = View.VISIBLE

            FirebaseAuth.getInstance()
                .sendPasswordResetEmail(email)
                .addOnCompleteListener { task ->
                    progressBar.visibility = View.GONE
                    if (task.isSuccessful) {
                        toast("Check your email")
                    } else {
                        toast(task.exception?.message!!)
                    }
                }
        }
    }
}