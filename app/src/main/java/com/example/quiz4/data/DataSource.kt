package com.example.quiz4.data

import com.example.quiz4.data.remote.model.UsersListItem
import kotlinx.coroutines.flow.Flow

interface DataSource {
    suspend fun getUsers(): List<UsersListItem>
     fun showInfo(id: String):Flow< UsersListItem>
}