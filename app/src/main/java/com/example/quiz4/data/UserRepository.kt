package com.example.quiz4.data

import androidx.lifecycle.LiveData
import com.example.quiz4.data.remote.model.UsersListItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
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

    fun showInfoUser(id: String): Flow<UsersListItem> {
        return remoteDataSource.showInfo(id)
    }
}