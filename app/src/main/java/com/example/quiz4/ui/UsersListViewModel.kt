package com.example.quiz4.ui

import androidx.lifecycle.*
import com.example.quiz4.data.UserRepository
import com.example.quiz4.data.local.model.User
import com.example.quiz4.data.local.model.UserWithHobbies
import com.example.quiz4.data.remote.model.UserInfo
import com.example.quiz4.data.remote.model.UsersListItem
import com.example.quiz4.util.Result
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class UsersListViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _userList = MutableStateFlow<Result<List<UsersListItem?>>>(Result.Success(emptyList()))
    val userList = _userList.asStateFlow()

    private val _showInfo = MutableStateFlow<Result<UsersListItem?>>(Result.Success(null))
    val showInfo = _showInfo.asStateFlow()

    private val _getUsersFromDataBase = MutableStateFlow<List<UserWithHobbies>>(emptyList())
    val getUsersFromDataBase = _getUsersFromDataBase.asStateFlow()

    private val _createUser = MutableSharedFlow<String>()
    val createUser = _createUser.asSharedFlow()

    fun getUsers() {
        viewModelScope.launch {
          userRepository.getUsers().collect{
              _userList.emit(it)
          }
        }
    }

    fun showInfoUser(id: String) {
        viewModelScope.launch {
            userRepository.showInfoUser(id).collect {
                _showInfo.emit(it)
            }
        }
    }

    fun createUser(user: UserInfo) {
        viewModelScope.launch {
            val data = userRepository.createUser(user)
            if (data.isSuccessful) {
                data.body()?.let { _createUser.emit(it) }
            }
        }
    }

    fun insertUser(user: User) {
        viewModelScope.launch {
            userRepository.insertUser(user)
        }
    }

    fun insertHobbies(id: String) {
        viewModelScope.launch {
            userRepository.insertHobbies(id)
        }
    }

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
}