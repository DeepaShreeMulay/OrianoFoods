package com.atlas.orianofood.mvvm.login.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.atlas.orianofood.mvvm.login.repository.LoginRepository
import com.google.gson.JsonObject
import kotlinx.coroutines.launch
import retrofit2.Response

class LoginViewModel(private val loginRepository: LoginRepository):ViewModel() {
    var myAuthResponse: MutableLiveData<Response<LoginData>> = MutableLiveData()

    fun authPost(jsonObject: JsonObject) {
        viewModelScope.launch {
            val authreponse: Response<LoginData> = loginRepository.authPost(jsonObject)
            myAuthResponse.value = authreponse
        }
    }
}