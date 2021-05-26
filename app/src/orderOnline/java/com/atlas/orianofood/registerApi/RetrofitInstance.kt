package com.atlas.orianofood.registerApi

import com.atlas.orianofood.constants.Constants.Companion.base_url
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private val client = OkHttpClient.Builder().build()
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(base_url)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

    }
    val api:SampleApi by lazy {
        retrofit.create(SampleApi::class.java)
    }
    val loginApi:AuthApi  by lazy {
        retrofit.create(AuthApi::class.java)
    }
}