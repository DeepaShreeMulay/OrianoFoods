package com.atlas.orianofood.utils

import android.content.Context
import android.content.SharedPreferences
import com.atlas.orianofood.R
import com.atlas.orianofood.core.App.Companion.appContext

object SharedpreferencesUtil {

    fun addToken(context: Context, token: String) {
        val sharedPreferences: SharedPreferences =
                context.getSharedPreferences(
                        context.getString(R.string.preference_file_key),
                        Context.MODE_PRIVATE
                )
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString("Token", token)
        editor.apply()
        editor.commit()
    }

    fun getToken(): String {
        val sharedPreferences: SharedPreferences =
                appContext.getSharedPreferences(
                        appContext.getString(R.string.preference_file_key),
                        Context.MODE_PRIVATE
                )
        return sharedPreferences.getString("Token", "") ?: ""
    }
}