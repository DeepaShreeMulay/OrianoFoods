package com.atlas.orianofood.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.atlas.orianofood.R
import com.atlas.orianofood.database.DatabaseHandler
import com.atlas.orianofood.utils.toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.android.synthetic.orderOnline.activity_profile.*

class ProfileActivity : AppCompatActivity() {
    /*private val DEFAULT_IMAGE_URL = "https://picsum.photos/200"

    private lateinit var imageUri: Uri
    private val REQUEST_IMAGE_CAPTURE = 100*/

    private val currentUser = FirebaseAuth.getInstance().currentUser
    val activity = this@ProfileActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        currentUser?.let { user ->


            edit_text_name.setText(user.displayName)
            text_email.text = if (user.email.isNullOrEmpty()) "Add Email" else user.email

            text_phone.text =
                if (user.phoneNumber.isNullOrEmpty()) "Add Number" else user.phoneNumber

            DatabaseHandler(this).createAddressTable()
            text_address.text = DatabaseHandler(this).getDefaultAddress()

            if (user.isEmailVerified) {
                text_not_verified.visibility = View.INVISIBLE
            } else {
                text_not_verified.visibility = View.VISIBLE
            }


        }

        button_save.setOnClickListener {

            val name = edit_text_name.text.toString().trim()

            if (name.isEmpty()) {
                edit_text_name.error = "name required"
                edit_text_name.requestFocus()
                return@setOnClickListener
            }

            val updates = UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .build()

            progressbar.visibility = View.VISIBLE

            currentUser?.updateProfile(updates)
                ?.addOnCompleteListener { task ->
                    progressbar.visibility = View.INVISIBLE
                    if (task.isSuccessful) {
                        toast("Profile Updated")
                    } else {
                        toast(task.exception?.message!!)
                    }
                }

        }


        text_not_verified.setOnClickListener {

            currentUser?.sendEmailVerification()
                ?.addOnCompleteListener {
                    if (it.isSuccessful) {
                        toast("Verification Email Sent")
                    } else {
                        toast(it.exception?.message!!)
                    }
                }

        }

        text_phone.setOnClickListener {

            val intent = Intent(activity, VerifyPhoneAuthActivity::class.java)
            startActivity(intent)
            finish()
        }

        text_email.setOnClickListener {
            val intent = Intent(activity, UpdateEmailActivity::class.java)
            startActivity(intent)
            finish()
        }

        text_password.setOnClickListener {
            val intent = Intent(activity, ChangePasswordActivity::class.java)
            startActivity(intent)
            finish()
        }

        text_address.setOnClickListener {

            val intent = Intent(activity, MyAddressesActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(activity, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}