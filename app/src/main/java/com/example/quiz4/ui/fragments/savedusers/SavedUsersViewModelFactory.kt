package com.example.quiz4.ui.fragments.savedusers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.quiz4.data.UserRepository

class SavedUsersViewModelFactory(private val userRepository: UserRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(SavedUsersViewModel::class.java)) {
            SavedUsersViewModel(userRepository) as T
        } else {
            modelClass.newInstance()
        }
    }
}