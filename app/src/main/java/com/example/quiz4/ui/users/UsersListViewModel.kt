package com.example.quiz4.ui.users

import androidx.lifecycle.*
import com.example.quiz4.data.UserRepository
import com.example.quiz4.data.remote.model.UsersListItem
import kotlinx.coroutines.launch

class UsersListViewModel(private val userRepository: UserRepository) : ViewModel() {


    private val _usersList = MutableLiveData<List<UsersListItem>>()
    val usersList: LiveData<List<UsersListItem>> = _usersList


    fun getUsers() {
        viewModelScope.launch {
            val data = userRepository.getUsers()
            _usersList.postValue(data)
        }
    }

    fun showInfoUser(id: String): LiveData<UsersListItem> {
        return userRepository.showInfoUser(id).asLiveData()
    }
}