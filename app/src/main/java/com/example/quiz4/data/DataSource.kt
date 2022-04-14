package com.example.quiz4.data

import com.example.quiz4.data.remote.model.UserInfo
import com.example.quiz4.data.remote.model.UsersListItem
import okhttp3.MultipartBody
import retrofit2.Response

interface DataSource {
    suspend fun getUsers(): Response<List<UsersListItem>>
    suspend fun showInfo(id: String): Response<UsersListItem>
    suspend fun createUser(user: UserInfo): Response<String>
    suspend fun uploadImage(id: String, image: MultipartBody.Part): Response<Any>
}