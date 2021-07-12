package com.atlas.orianofood.mvvm.reciver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.atlas.orianofood.firebaseRT.utils.HOMESPACTIVITYCONTEXT

class StateChangeReciver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (stateChangeListener == null) {
            stateChangeListener = HOMESPACTIVITYCONTEXT as StateChangeListener
        }
        if (stateChangeListener != null) {
            Toast.makeText(
                context,
                "from : " + context?.applicationInfo?.className,
                Toast.LENGTH_LONG
            ).show()
            stateChangeListener!!.onStateChanged(intent?.getStringExtra("myExtra"))
        }
    }

    interface StateChangeListener {
        fun onStateChanged(sessionStates: String?)
    }

    companion object {
        var stateChangeListener: StateChangeListener? = null
    }
}