package com.example.quiz4.data.local

import com.example.quiz4.data.local.db.UserDao
import com.example.quiz4.data.local.model.Hobie
import com.example.quiz4.data.local.model.User
import com.example.quiz4.data.local.model.UserWithHobbies
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val dao: UserDao) {

    fun gerUsers(): Flow<List<UserWithHobbies>> = dao.getUsers()
    suspend fun insertUser(user: User) = dao.insertUser(user)
    suspend fun deleteUser(id: String) = dao.deleteUser(id)
    suspend fun updateUser(user: User) = dao.updateUser(user)
    suspend fun insertHobbies(hobbies: List<Hobie>) = dao.insertHobbies(hobbies)
}