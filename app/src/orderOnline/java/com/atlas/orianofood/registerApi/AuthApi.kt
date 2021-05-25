package com.atlas.orianofood.registerApi

import com.atlas.orianofood.model_Register.LoginInfo
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface AuthApi {
    @Headers("Content-Type: application/json")
    @POST("Auth")
    suspend fun authPost(
            @Body loginInfo: LoginInfo
    ): Response<LoginInfo>
}