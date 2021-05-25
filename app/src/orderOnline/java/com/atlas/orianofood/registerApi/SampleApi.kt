package com.atlas.orianofood.registerApi

import com.atlas.orianofood.model_Register.UserInfo
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface SampleApi {
    @Headers("Content-Type: application/json")
    @POST("register")
    suspend fun pushPost(
        @Body userInfo: UserInfo
    ): Response<UserInfo>


}