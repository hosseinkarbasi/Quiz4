package com.example.quiz4.ui.fragments.users

import androidx.lifecycle.*
import com.example.quiz4.data.UserRepository
import com.example.quiz4.data.local.model.User
import com.example.quiz4.util.Result
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class UsersListViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val channel = Channel<Boolean> { }

    init {
        retry()
    }

    val getUsers = channel.receiveAsFlow().flatMapLatest {
        userRepository.getUsers()
    }.stateIn(
        initialValue = Result.Loading(),
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L)
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

    fun insertHobbies(id: String) {
        viewModelScope.launch {
            userRepository.insertHobbies(id)
        }
    }
}