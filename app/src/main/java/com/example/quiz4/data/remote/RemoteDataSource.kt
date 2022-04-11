package com.example.quiz4.data.remote

import com.example.quiz4.data.DataSource
import com.example.quiz4.data.local.model.User
import com.example.quiz4.data.remote.model.UserInfo
import com.example.quiz4.data.remote.model.UsersListItem
import com.example.quiz4.data.remote.network.UserApi
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class RemoteDataSource(private val userApi: UserApi) : DataSource {
    override suspend fun getUsers(): Response<List<UsersListItem>> {
        return userApi.getUsers()
    }

    override suspend fun showInfo(id: String): Response<UsersListItem> {
        return userApi.getShowInfo(id)
    }

    override suspend fun createUser(user: UserInfo): Response<String> {
        return userApi.createAccount(user)
    }
}