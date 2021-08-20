package com.atlas.orianofood.mvvm.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class DataSetChangedReceiver : BroadcastReceiver() {
    interface DataSetChangeListener {
        fun onDataSetChanged()
    }

    companion object {
        var dataSetChangeListener: DataSetChangeListener? = null
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        if (dataSetChangeListener != null) {
            dataSetChangeListener!!.onDataSetChanged()
        }
    }
}