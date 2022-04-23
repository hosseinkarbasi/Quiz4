package com.example.quiz4.data.repository

import com.example.quiz4.data.local.db.UserDao
import com.example.quiz4.data.local.model.User
import com.example.quiz4.data.local.model.UserWithHobbies
import com.example.quiz4.data.remote.model.UserInfo
import com.example.quiz4.data.remote.model.UsersListItem
import com.example.quiz4.data.remote.network.UserApi
import com.example.quiz4.util.Mapper
import com.example.quiz4.util.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val remoteDataSource: UserApi,
    private val localDataSource: UserDao
) {

    suspend fun getUsers(): Flow<Result<List<UsersListItem>>> {
        return requestFlow { remoteDataSource.getUsers() }
    }

    suspend fun showInfoUser(id: String): Flow<Result<UsersListItem>> {
        return requestFlow { remoteDataSource.getShowInfo(id) }

    }

    suspend fun createUser(user: UserInfo): Response<String> {
        val data: Response<String>
        withContext(Dispatchers.IO) {
            data = remoteDataSource.createAccount(user)
        }
        return data
    }

    suspend fun uploadImage(id: String, image: MultipartBody.Part) {
        remoteDataSource.uploadImage(id, image)
    }

    suspend fun insertUser(user: User) {
        withContext(Dispatchers.IO) {
            localDataSource.insertUser(user)
        }
    }

    suspend fun insertHobbies(id: String) {
        withContext(Dispatchers.IO) {
            val user = remoteDataSource.getShowInfo(id)
            val hobbies = Mapper.transformToHobie(user.body()!!)
            localDataSource.insertHobbies(hobbies)
        }
    }

    fun getUsersFromDataBase(): Flow<List<UserWithHobbies>> {
        return localDataSource.getUsers()
    }

    suspend fun deleteUser(id: String) {
        withContext(Dispatchers.IO) {
            localDataSource.deleteUser(id)
        }
    }

    suspend fun updateUser(user: User) {
        withContext(Dispatchers.IO) {
            localDataSource.updateUser(user)
        }
    }

    private inline fun <T> requestFlow(
        crossinline apiCall: suspend () -> Response<T>
    ): Flow<Result<T>> {
        return flow {
            try {
                emit(Result.Loading())
                val response = apiCall()
                if (response.isSuccessful) {
                    response.body()?.let {
                        emit(Result.Success(it))
                    }
                }
            } catch (e: Exception) {
                emit(Result.Error("Please Check Your Internet"))
            }
        }
    }
}