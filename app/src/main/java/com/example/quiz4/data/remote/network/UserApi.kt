package com.example.quiz4.data.remote.network

import com.example.quiz4.data.remote.model.UserInfo
import com.example.quiz4.data.remote.model.UsersListItem
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.http.*

interface UserApi {

    @GET("users")
    suspend fun getUsers(@QueryMap users: HashMap<String, String> = hashMapOf()): List<UsersListItem>

    @GET("http://papp.ir/api/v1/users/{id}")
    suspend fun getShowInfo(@Path("id") id: String): UsersListItem

    @POST("users")
    suspend fun createAccount(@Body userData: UserInfo): String
}