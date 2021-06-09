package com.atlas.orianofood.mvvm.register.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.atlas.orianofood.mvvm.register.repository.RegisterRepository

class RegisterViewModelFactory(private val repository: RegisterRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return RegisterViewModel(repository) as T
    }

}