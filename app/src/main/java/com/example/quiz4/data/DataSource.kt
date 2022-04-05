package com.example.quiz4.data

import com.example.quiz4.data.remote.model.UserInfo
import com.example.quiz4.data.remote.model.UsersListItem
import kotlinx.coroutines.flow.Flow

interface DataSource {
    suspend fun getUsers(): List<UsersListItem>
    suspend fun showInfo(id: String): UsersListItem
    suspend fun createUser(user: UserInfo): String
}