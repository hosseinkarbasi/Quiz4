package com.example.quiz4.data.remote.network

import com.example.quiz4.data.remote.model.UsersListItem
import kotlinx.coroutines.flow.Flow
import retrofit2.http.*

interface UserApi {

    @GET("users")
    suspend fun getUsers(@QueryMap users: HashMap<String, String> = hashMapOf()): List<UsersListItem>

    @GET("http://papp.ir/api/v1/users/{id}")
     fun getShowInfo(@Path("id") id: String):Flow<UsersListItem>
}