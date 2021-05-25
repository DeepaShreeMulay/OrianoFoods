package com.atlas.orianofood.repository

import com.atlas.orianofood.dao.LoginDao
import com.atlas.orianofood.model_Register.LoginInfo
import com.atlas.orianofood.registerApi.RetrofitInstance
import retrofit2.Response

class LoginRepository(private  val dao:LoginDao) {
    //auth api
    suspend fun authPost(loginInfo: LoginInfo): Response<LoginInfo> {
        return RetrofitInstance.loginApi.authPost(loginInfo)
    }
}