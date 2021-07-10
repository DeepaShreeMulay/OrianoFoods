package com.atlas.orianofood.firebaseRT.utils

import android.content.Context
import android.content.Intent
import com.atlas.orianofood.firebaseRT.model.User
import com.atlas.orianofood.mvvm.reciver.StateChangeReciver


object Common {
    var currentUser: User? = null
    fun sendStateChangedBroadCast(context: Context, state: String) {
        val intent = Intent(context, StateChangeReciver::class.java)
        intent.action = "com.atlas.orianofood.CHANGED"
        intent.putExtra("myExtra", state)
        context.sendBroadcast(intent)
    }
}
