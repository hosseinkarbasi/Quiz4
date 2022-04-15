package com.example.quiz4.ui.fragments.showusersinfo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.quiz4.data.UserRepository

class ShowInfoViewModelFactory(private val userRepository: UserRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(ShowInfoVIewModel::class.java)) {
            ShowInfoVIewModel(userRepository) as T
        } else {
            modelClass.newInstance()
        }
    }
}