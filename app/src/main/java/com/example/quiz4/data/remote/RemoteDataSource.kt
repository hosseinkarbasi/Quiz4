package com.example.quiz4.data.remote

import com.example.quiz4.data.remote.model.UserInfo
import com.example.quiz4.data.remote.model.UsersListItem
import com.example.quiz4.data.remote.network.UserApi
import okhttp3.MultipartBody
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val service: UserApi) {

    suspend fun getUsers(): Response<List<UsersListItem>> = service.getUsers()
    suspend fun getShowInfo(id: String) = service.getShowInfo(id)
    suspend fun createAccount(user: UserInfo) = service.createAccount(user)
    suspend fun uploadImage(id: String, image: MultipartBody.Part) = service.uploadImage(id, image)

}