package com.atlas.orianofood.utils

import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.atlas.orianofood.activity.AddAddressActivity
import com.atlas.orianofood.activity.HomeActivity
import com.atlas.orianofood.activity.ProfileActivity
import com.atlas.orianofood.activity.SignInActivity
import com.atlas.orianofood.database.DatabaseHandler
import com.google.firebase.auth.FirebaseAuth


fun Context.toast(message: String) =
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

fun Context.login() {
    val currentUser = FirebaseAuth.getInstance().currentUser
    var intent = Intent(this, HomeActivity::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }
    if (currentUser != null) {
        when {
            currentUser.displayName.isNullOrEmpty() -> {
                intent = Intent(this, ProfileActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }
            }
            currentUser.phoneNumber.isNullOrEmpty() -> {
                intent = Intent(this, ProfileActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }
            }
            (DatabaseHandler(this).getDefaultAddress() == "No Default Address Available") -> {
                intent = Intent(this, AddAddressActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }
            }
        }
    }

    startActivity(intent)
}


fun Context.logout() {
    FirebaseAuth.getInstance().signOut()
    val intent = Intent(this, SignInActivity::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }
    startActivity(intent)
}
