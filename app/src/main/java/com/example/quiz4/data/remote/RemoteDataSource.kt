package com.example.quiz4.data.remote

import com.example.quiz4.data.DataSource
import com.example.quiz4.data.remote.model.UsersListItem
import com.example.quiz4.data.remote.network.UserApi
import kotlinx.coroutines.flow.Flow

class RemoteDataSource(private val userApi: UserApi) : DataSource {
    override suspend fun getUsers(): List<UsersListItem> {
        return userApi.getUsers()
    }

    override fun showInfo(id: String): Flow<UsersListItem> {
        return userApi.getShowInfo(id)
    }
}