package com.example.quiz4.ui.users

import androidx.lifecycle.*
import com.example.quiz4.data.UserRepository
import com.example.quiz4.data.remote.model.UserInfo
import com.example.quiz4.data.remote.model.UsersListItem
import kotlinx.coroutines.launch

class UsersListViewModel(private val userRepository: UserRepository) : ViewModel() {


    private val _usersList = MutableLiveData<List<UsersListItem>>()
    val usersList: LiveData<List<UsersListItem>> = _usersList

    private val _createUser = MutableLiveData<String>()
    val createUser: LiveData<String> = _createUser

    private val _showInfo = MutableLiveData<UsersListItem>()
    val showInfo: LiveData<UsersListItem> = _showInfo


    fun getUsers() {
        viewModelScope.launch {
            val data = userRepository.getUsers()
            _usersList.postValue(data)
        }
    }

    fun showInfoUser(id: String) {
        viewModelScope.launch {
            val data = userRepository.showInfoUser(id)
            _showInfo.postValue(data)
        }
    }

    fun createUser(user: UserInfo) {
        viewModelScope.launch {
            val data = userRepository.createUser(user)
            _createUser.postValue(data)
        }
    }
}