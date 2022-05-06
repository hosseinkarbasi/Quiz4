package com.example.quiz4.ui.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quiz4.data.remote.model.UserInfo
import com.example.quiz4.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(private val userRepository: UserRepository) :
    ViewModel() {

    fun createUser(user: UserInfo) {
        viewModelScope.launch {
            userRepository.createUser(user)
        }
    }
}