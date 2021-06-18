package com.atlas.orianofood.mvvm.utils

import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.atlas.orianofood.firebaseRT.activity.SignInActivity
import com.atlas.orianofood.mvvm.utils.SharedpreferencesUtil.clearedSharedPref


fun Context.toast(message: String) =
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

fun Context.logout() {
    clearedSharedPref()
    val intent = Intent(this, SignInActivity::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }
    startActivity(intent)
}