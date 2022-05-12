package com.example.quiz4.ui.fragments.users

import androidx.lifecycle.*
import com.example.quiz4.data.repository.UserRepository
import com.example.quiz4.data.local.model.User
import com.example.quiz4.data.remote.model.UserInfo
import com.example.quiz4.data.remote.model.UsersListItem
import com.example.quiz4.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersListViewModel @Inject constructor
    (private val userRepository: UserRepository) : ViewModel() {

    private val _getUser2: MutableStateFlow<Result<List<UsersListItem>>> =
        MutableStateFlow(Result.Loading())
    val getUser2 = _getUser2.asStateFlow()


    fun createUser(user: UserInfo) {
        viewModelScope.launch {
            userRepository.createUser(user)
        }
        getUsers()
    }

    fun getUsers() {
        viewModelScope.launch {
            userRepository.getUsers().collect {
                _getUser2.emit(it)
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
}