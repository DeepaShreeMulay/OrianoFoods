package com.atlas.orianofood.mvvm.setProfile.repository

import com.atlas.orianofood.mvvm.retrofit.RetrofitInstance
import com.atlas.orianofood.mvvm.setProfile.SetProfileDao
import com.atlas.orianofood.mvvm.setProfile.SetProfileResponse
import com.google.gson.JsonObject
import retrofit2.Response

class SetProfileRepository(private val dao: SetProfileDao) {
    suspend fun changePassword(jsonObject: JsonObject): Response<SetProfileResponse> {
        return RetrofitInstance.api.changePassword(jsonObject)
    }


}