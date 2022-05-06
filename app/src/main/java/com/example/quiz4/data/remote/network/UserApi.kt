package com.example.quiz4.data.remote.network

import com.example.quiz4.data.remote.model.UserInfo
import com.example.quiz4.data.remote.model.UsersListItem
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface UserApi {

    @GET("users")
    suspend fun getUsers(): Response<List<UsersListItem>>

    @GET("users/{id}")
    suspend fun getShowInfo(@Path("id") id: String): Response<UsersListItem>

    @POST("users")
    suspend fun createAccount(@Body userData: UserInfo): Response<String>

    @Multipart
    @POST("users/{id}/image")
    suspend fun uploadImage(@Path("id") id: String, @Part image: MultipartBody.Part): Response<Any>
}