package com.atlas.orianofood.model_Register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.atlas.orianofood.repository.Repository
import com.google.gson.JsonObject
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel (private val repository: Repository):ViewModel(){
    var myResponse: MutableLiveData<Response<RegisterResponse>> = MutableLiveData()

    fun pushPost(jsonObject: JsonObject) {
        viewModelScope.launch {
            val response: Response<RegisterResponse> = repository.pushPost(jsonObject)
            myResponse.value = response
        }
    }
}