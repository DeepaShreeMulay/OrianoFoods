package com.atlas.orianofood.model_Register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.atlas.orianofood.repository.LoginRepository

class LoginModelFactory(private val loginRepository: LoginRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LoginViewModel(loginRepository)as T
    }

}