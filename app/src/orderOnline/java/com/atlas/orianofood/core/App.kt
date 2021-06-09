package com.atlas.orianofood.core

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.atlas.orianofood.mvvm.retrofit.ApiService
import kotlin.properties.Delegates

class App : Application(){
    companion object {

        lateinit var appContext: Context
        var api: ApiService? = null
        private val TAG = App::class.java.simpleName
        var instance: App by Delegates.notNull()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        appContext = this
        MultiDex.install(this)
       /* val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val httpClient = OkHttpClient.Builder()

        val client = httpClient.connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(180, TimeUnit.SECONDS)
                .connectTimeout(180, TimeUnit.SECONDS)
                .writeTimeout(180, TimeUnit.SECONDS).build()*/



    }
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}