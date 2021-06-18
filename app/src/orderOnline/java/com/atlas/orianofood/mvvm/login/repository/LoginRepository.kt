package com.atlas.orianofood.mvvm.login.repository

import com.atlas.orianofood.mvvm.login.dao.LoginDao
import com.atlas.orianofood.mvvm.login.model.LoginData
import com.atlas.orianofood.mvvm.retrofit.RetrofitInstance
import com.google.gson.JsonObject
import retrofit2.Response


class LoginRepository(private val dao: LoginDao) {
    //auth api
    suspend fun loginByMobile(jsonObject: JsonObject): Response<LoginData> {
        return RetrofitInstance.api.loginByMobile(jsonObject)
    }
}