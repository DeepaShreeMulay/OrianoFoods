package com.atlas.orianofood.model_Register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.atlas.orianofood.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel (private val repository: Repository):ViewModel(){
    var myResponse: MutableLiveData<Response<UserInfo>> = MutableLiveData()

    fun pushPost(userInfo: UserInfo){
        viewModelScope.launch {
            val response:Response<UserInfo> = repository.pushPost(userInfo)
            myResponse.value=response
        }
    }
}