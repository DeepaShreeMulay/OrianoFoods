package com.atlas.orianofood.model_Register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.atlas.orianofood.repository.LoginRepository
import com.google.gson.JsonObject
import kotlinx.coroutines.launch
import retrofit2.Response

class LoginViewModel(private val loginRepository: LoginRepository):ViewModel() {
    var myAuthResponse: MutableLiveData<Response<LoginInfo>> = MutableLiveData()

    fun authPost(jsonObject: JsonObject) {
        viewModelScope.launch {
            val authreponse: Response<LoginInfo> = loginRepository.authPost(jsonObject)
            myAuthResponse.value = authreponse
        }
    }
}