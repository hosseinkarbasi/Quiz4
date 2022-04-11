package com.example.quiz4.data.local

import com.example.quiz4.data.local.db.UserDao
import com.example.quiz4.data.local.model.Hobie
import com.example.quiz4.data.local.model.User
import com.example.quiz4.data.local.model.UserWithHobbies

class LocalDataSource(private val userDao: UserDao) : IDataSource {

    override suspend fun insertUser(user: User) {
        userDao.insertUser(user)
    }

    override suspend fun insertHobbies(hobbies: List<Hobie>) {
        userDao.insertHobbies(hobbies)
    }

    override suspend fun getUsers(): List<UserWithHobbies> {
        return userDao.getUsers()
    }

    override suspend fun deleteUser(id: String) {
        return userDao.deleteUser(id)
    }

    override suspend fun updateUser(user: User) {
        userDao.updateUser(user)
    }

    override suspend fun updateHobbies(hobbies: List<Hobie>) {
        userDao.updateHobbies(hobbies)
    }
}