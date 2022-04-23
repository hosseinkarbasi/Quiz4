package com.example.quiz4.ui.fragments.savedusers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quiz4.data.repository.UserRepository
import com.example.quiz4.data.local.model.User
import com.example.quiz4.data.local.model.UserWithHobbies
import com.example.quiz4.data.remote.model.UserInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavedUsersViewModel @Inject constructor
    (private val userRepository: UserRepository) : ViewModel() {

    private val _getUsersFromDataBase = MutableStateFlow<List<UserWithHobbies>>(emptyList())
    val getUsersFromDataBase = _getUsersFromDataBase.asStateFlow()

    init {
        getUsersFromDataBase()
    }

    private fun getUsersFromDataBase() {
        viewModelScope.launch {
            userRepository.getUsersFromDataBase().collect{
                _getUsersFromDataBase.emit(it)
            }
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

    fun createNewUser(user:UserInfo){
        viewModelScope.launch {
            userRepository.createUser(user)
        }
    }
}