package com.example.quiz4.data.remote

import com.example.quiz4.data.DataSource
import com.example.quiz4.data.remote.model.UserInfo
import com.example.quiz4.data.remote.model.UsersListItem
import com.example.quiz4.data.remote.network.UserApi
import kotlinx.coroutines.flow.Flow

class RemoteDataSource(private val userApi: UserApi) : DataSource {
    override suspend fun getUsers(): List<UsersListItem> {
        return userApi.getUsers()
    }

    override suspend fun showInfo(id: String): UsersListItem {
        return userApi.getShowInfo(id)
    }

    override suspend fun createUser(user: UserInfo): String {
        return userApi.createAccount(user)
    }
}