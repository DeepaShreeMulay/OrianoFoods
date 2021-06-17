package com.atlas.orianofood.mvvm.retrofit

import com.atlas.orianofood.mvvm.login.model.LoginData
import com.atlas.orianofood.mvvm.register.model.RegisterResponse
import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface AuthApiService {
    @Headers("Content-Type: application/json")
    @POST("mobile_Auth")
    suspend fun authPost(
        @Body jsonObject: JsonObject
    ): Response<LoginData>

    @Headers("Content-Type: application/json")
    @POST("mobile_register")
    suspend fun pushPost(
        @Body jsonObject: JsonObject
    ): Response<RegisterResponse>
}