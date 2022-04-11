package com.example.quiz4.data.local

import com.example.quiz4.data.local.model.Hobie
import com.example.quiz4.data.local.model.User
import com.example.quiz4.data.local.model.UserWithHobbies
import kotlinx.coroutines.flow.MutableStateFlow

interface IDataSource {
    suspend fun insertUser(user: User)
    suspend fun insertHobbies(hobbies: List<Hobie>)
    suspend fun getUsers(): List<UserWithHobbies>
    suspend fun deleteUser(id: String)
    suspend fun updateUser(user: User)
    suspend fun updateHobbies(hobbies: List<Hobie>)
}