package com.atlas.orianofood.mvvm.setProfile.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.atlas.orianofood.mvvm.setProfile.SetProfileResponse
import com.atlas.orianofood.mvvm.setProfile.repository.SetProfileRepository
import com.google.gson.JsonObject
import kotlinx.coroutines.launch
import retrofit2.Response

class SetProfileViewModel(private val repository: SetProfileRepository) : ViewModel() {
    var myResponse: MutableLiveData<Response<SetProfileResponse>> = MutableLiveData()

    fun changePassword(jsonObject: JsonObject) {
        viewModelScope.launch {
            val response: Response<SetProfileResponse> = repository.changePassword(jsonObject)
            myResponse.value = response
        }
    }

}