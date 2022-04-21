package com.example.quiz4.ui.fragments.users

import androidx.lifecycle.*
import com.example.quiz4.data.repository.UserRepository
import com.example.quiz4.data.local.model.User
import com.example.quiz4.data.remote.model.UserInfo
import com.example.quiz4.data.remote.model.UsersListItem
import com.example.quiz4.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersListViewModel @Inject constructor
    (private val userRepository: UserRepository) : ViewModel() {

    private val channel = Channel<Boolean> { }

    init {
        retry()
    }

    val getUsers = channel.receiveAsFlow().flatMapLatest {
        userRepository.getUsers()
    }.stateIn(
        initialValue = Result.Loading(),
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(1000L)
    )

    fun retry() {
        viewModelScope.launch {
            channel.send(true)
        }
    }

    fun insertUser(user: User) {
        viewModelScope.launch {
            userRepository.insertUser(user)
        }
    }

    fun createUser(user: UserInfo) {
        viewModelScope.launch {
            userRepository.createUser(user)
        }
    }

    fun insertHobbies(id: String) {
        viewModelScope.launch {
            userRepository.insertHobbies(id)
        }
    }
}