package com.atlas.orianofood.registerApi

import com.atlas.orianofood.model_Register.RegisterResponse
import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface SampleApi {
    @Headers("Content-Type: application/json")
    @POST("register")
    suspend fun pushPost(
        @Body jsonObject: JsonObject
    ): Response<RegisterResponse>


}