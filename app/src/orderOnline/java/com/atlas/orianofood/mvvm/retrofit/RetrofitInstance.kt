package com.atlas.orianofood.mvvm.retrofit

import com.atlas.orianofood.firebaseRT.utils.SharedpreferencesUtil.getToken
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    const val base_url = "http://test.wapi.fobisol.com/api/"

    private val client = OkHttpClient.Builder().build()

    private val gson = GsonBuilder()
        .setLenient()
        .create()

    private val clientWithAuth = OkHttpClient.Builder()
        .retryOnConnectionFailure(true)
        .addInterceptor { chain ->
            val newRequest =
                chain.request().newBuilder()
                    .addHeader("Authorization", getToken())
                    .addHeader("Accept", "application/json")
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Cache-Control", "no-cache")
                    .addHeader("Connection", "close")
                    .build()

            chain.proceed(newRequest)

        }.build()

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(base_url)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

    }


    private val retrofitWithAuth by lazy {
        Retrofit.Builder()
            .baseUrl(base_url)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(clientWithAuth)
            .build()

    }

    val api: AuthApiService by lazy {
        retrofit.create(AuthApiService::class.java)
    }
    val apiService: ApiService by lazy {
        retrofitWithAuth.create(ApiService::class.java)
    }
}