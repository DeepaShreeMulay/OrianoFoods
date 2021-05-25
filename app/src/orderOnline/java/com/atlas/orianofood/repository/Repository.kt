package com.atlas.orianofood.repository

import com.atlas.orianofood.dao.UserDao
import com.atlas.orianofood.model_Register.UserInfo
import com.atlas.orianofood.registerApi.RetrofitInstance
import retrofit2.Response

class Repository (private val dao: UserDao){
    suspend fun pushPost(userInfo: UserInfo): Response<UserInfo> {
        return RetrofitInstance.api.pushPost(userInfo)
    }

}