package com.atlas.orianofood.mvvm.login.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.atlas.orianofood.mvvm.login.repository.LoginRepository

class LoginModelFactory(private val loginRepository: LoginRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LoginViewModel(loginRepository) as T
    }

}