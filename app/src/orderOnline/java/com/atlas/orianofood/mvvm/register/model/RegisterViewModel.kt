package com.atlas.orianofood.mvvm.register.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.atlas.orianofood.mvvm.register.repository.RegisterRepository
import com.google.gson.JsonObject
import kotlinx.coroutines.launch
import retrofit2.Response

class RegisterViewModel(private val repository: RegisterRepository) : ViewModel() {
    var myResponse: MutableLiveData<Response<RegisterResponse>> = MutableLiveData()

    fun registerByMobile(jsonObject: JsonObject) {
        viewModelScope.launch {
            val response: Response<RegisterResponse> = repository.registerByMobile(jsonObject)
            myResponse.value = response
        }
    }

}