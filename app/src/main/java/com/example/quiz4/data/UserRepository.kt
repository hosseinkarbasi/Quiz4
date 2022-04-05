package com.example.quiz4.data

import com.example.quiz4.data.remote.model.UserInfo
import com.example.quiz4.data.remote.model.UsersListItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepository(
    private val remoteDataSource: DataSource
) {

    suspend fun getUsers(): List<UsersListItem> {
        val data: List<UsersListItem>
        withContext(Dispatchers.IO) {
            data = remoteDataSource.getUsers()
        }
        return data
    }

    suspend fun showInfoUser(id: String): UsersListItem {
        val data: UsersListItem
        withContext(Dispatchers.IO) {
            data = remoteDataSource.showInfo(id)
        }
        return data

    }

    suspend fun createUser(user: UserInfo): String {
        val data: String
        withContext(Dispatchers.IO) {
            data = remoteDataSource.createUser(user)
        }
        return data
    }
}