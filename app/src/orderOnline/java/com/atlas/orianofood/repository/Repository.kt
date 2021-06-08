package com.atlas.orianofood.repository

import com.atlas.orianofood.dao.UserDao
import com.atlas.orianofood.model_Register.RegisterResponse
import com.atlas.orianofood.registerApi.RetrofitInstance
import com.google.gson.JsonObject
import retrofit2.Response

class Repository (private val dao: UserDao) {
    suspend fun pushPost(jsonObject: JsonObject): Response<RegisterResponse> {
        return RetrofitInstance.api.pushPost(jsonObject)
    }


}