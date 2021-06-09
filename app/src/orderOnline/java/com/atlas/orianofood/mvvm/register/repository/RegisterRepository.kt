package com.atlas.orianofood.mvvm.register.repository

import com.atlas.orianofood.mvvm.register.dao.UserDao
import com.atlas.orianofood.mvvm.register.model.RegisterResponse
import com.atlas.orianofood.mvvm.retrofit.RetrofitInstance
import com.google.gson.JsonObject
import retrofit2.Response

class RegisterRepository(private val dao: UserDao) {
    suspend fun pushPost(jsonObject: JsonObject): Response<RegisterResponse> {
        return RetrofitInstance.api.pushPost(jsonObject)
    }


}