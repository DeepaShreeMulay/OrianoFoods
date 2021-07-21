package com.atlas.orianofood.mvvm.setProfile.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.atlas.orianofood.mvvm.setProfile.repository.SetProfileRepository

class SetProfileViewModelFactory(private val repository: SetProfileRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SetProfileViewModel(repository) as T
    }

}