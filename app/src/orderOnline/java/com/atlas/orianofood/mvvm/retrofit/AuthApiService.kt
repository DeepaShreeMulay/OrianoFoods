package com.atlas.orianofood.mvvm.retrofit

import com.atlas.orianofood.mvvm.login.model.LoginData
import com.atlas.orianofood.mvvm.register.model.RegisterResponse
import com.atlas.orianofood.mvvm.setProfile.SetProfileResponse
import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface AuthApiService {
    @Headers("Content-Type: application/json")
    @POST("mobile_Auth")
    suspend fun loginByMobile(
        @Body jsonObject: JsonObject
    ): Response<LoginData>

    @Headers("Content-Type: application/json")
    @POST("mobile_register")
    suspend fun registerByMobile(
        @Body jsonObject: JsonObject
    ): Response<RegisterResponse>

    @Headers("Content-Type: application/json")
    @POST("email_Auth")
    suspend fun loginByEmail(
        @Body jsonObject: JsonObject
    ): Response<LoginData>

    @Headers("Content-Type: application/json")
    @POST("email_register")
    suspend fun registerByEmail(
        @Body jsonObject: JsonObject
    ): Response<RegisterResponse>

    @Headers("Authorization: Bearer pZZblQLaDAvHXkZtHLHFfGwAfPEN90XvfI1pIQip")
    @POST("v1/my-order")
    suspend fun myOrder(
        @Body jsonObject: JsonObject
    )

    @Headers("Authorization: Bearer oP6eJWhqsD7ggORRgRQqElgsdPiFFTbXvFQNwbs5")
    @POST("v1/set-profile/29")
    suspend fun changePassword(
        @Body jsonObject: JsonObject
    ): Response<SetProfileResponse>


}