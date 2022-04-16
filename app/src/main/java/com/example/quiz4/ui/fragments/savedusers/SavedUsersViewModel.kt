package com.example.quiz4.ui.fragments.savedusers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quiz4.data.repository.UserRepository
import com.example.quiz4.data.local.model.User
import com.example.quiz4.data.local.model.UserWithHobbies
import com.example.quiz4.data.remote.model.UserInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavedUsersViewModel @Inject constructor
    (private val userRepository: UserRepository):ViewModel() {

    private val _getUsersFromDataBase = MutableStateFlow<List<UserWithHobbies>>(emptyList())
    val getUsersFromDataBase = _getUsersFromDataBase.asStateFlow()

    fun getUsersFromDataBase() {
        viewModelScope.launch {
            val data = userRepository.getUsersFromDataBase()
            _getUsersFromDataBase.emit(data)
        }
    }

    fun deleteUser(id: String) {
        viewModelScope.launch {
            userRepository.deleteUser(id)
        }
    }

    fun updateUser(user: User) {
        viewModelScope.launch {
            userRepository.updateUser(user)
        }
    }

    fun createUser(user: UserInfo) {
        viewModelScope.launch {
            userRepository.createUser(user)
        }
    }
}