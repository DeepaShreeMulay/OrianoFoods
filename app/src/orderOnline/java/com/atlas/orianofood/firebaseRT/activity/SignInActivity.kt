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
import kotlinx.android.synthetic.orderOnline.activity_signin.*

class SignInActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    val activity = this@SignInActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)

        mAuth = FirebaseAuth.getInstance()

        btn_signIn.setOnClickListener {
            val email = et_email.text.toString().trim()
            val password = et_password.text.toString().trim()

            if (email.isEmpty()) {
                et_email.error = "Email Required"
                et_email.requestFocus()
                return@setOnClickListener
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                et_email.error = "Valid Email Required"
                et_email.requestFocus()
                return@setOnClickListener
            }

            if (password.isEmpty() || password.length < 6) {
                et_password.error = "Minimum 6 char password required"
                et_password.requestFocus()
                return@setOnClickListener
            }

            loginUser(email, password)
        }

        et_signUP.setOnClickListener {
            startActivity(Intent(activity, SignUpActivity::class.java))
        }

        text_view_forget_password.setOnClickListener {
            startActivity(Intent(activity, ResetPasswordActivity::class.java))
        }

        iv_facebook.setOnClickListener {
            //startActivity(Intent(activity, SignUpActivity::class.java))
            toast("Work in Progress. Soon you will see this feature.")
        }

        iv_google.setOnClickListener {
            //startActivity(Intent(activity, SignUpActivity::class.java))
            toast("Work in Progress. Soon you will see this feature.")
        }

        iv_phone.setOnClickListener {
            //startActivity(Intent(activity, SignUpActivity::class.java))
            //toast("Work in Progress. Soon you will see this feature.")
            val intent = Intent(activity, VerifyPhoneAuthActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun loginUser(email: String, password: String) {
        progressBar.visibility = View.VISIBLE
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                progressBar.visibility = View.GONE
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