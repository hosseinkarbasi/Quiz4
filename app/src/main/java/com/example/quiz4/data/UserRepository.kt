package com.example.quiz4.data

import com.example.quiz4.data.local.ILocalDataSource
import com.example.quiz4.data.local.model.User
import com.example.quiz4.data.local.model.UserWithHobbies
import com.example.quiz4.data.remote.IRemoteDataSource
import com.example.quiz4.data.remote.model.UserInfo
import com.example.quiz4.data.remote.model.UsersListItem
import com.example.quiz4.util.Mapper
import com.example.quiz4.util.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import retrofit2.Response

class UserRepository(
    private val remoteIRemoteDataSource: IRemoteDataSource,
    private val localLocalDataSource: ILocalDataSource
) {

    suspend fun getUsers(): Flow<Result<List<UsersListItem>>> {

        return requestFlow { remoteIRemoteDataSource.getUsers() }
    }

    suspend fun showInfoUser(id: String): Flow<Result<UsersListItem>> {
        return requestFlow { remoteIRemoteDataSource.showInfo(id) }

    }

    suspend fun createUser(user: UserInfo): Response<String> {
        val data: Response<String>
        withContext(Dispatchers.IO) {
            data = remoteIRemoteDataSource.createUser(user)
        }
        return data
    }

    suspend fun uploadImage(id: String, image: MultipartBody.Part) {
        remoteIRemoteDataSource.uploadImage(id, image)
    }

    suspend fun insertUser(user: User) {
        withContext(Dispatchers.IO) {
            localLocalDataSource.insertUser(user)
        }
    }

    suspend fun insertHobbies(id: String) {
        withContext(Dispatchers.IO) {
            val user = remoteIRemoteDataSource.showInfo(id)
            val hobbies = Mapper.transformToHobie(user.body()!!)
            localLocalDataSource.insertHobbies(hobbies)
        }
    }

    suspend fun getUsersFromDataBase(): List<UserWithHobbies> {
        return localLocalDataSource.getUsers()
    }

    suspend fun deleteUser(id: String) {
        withContext(Dispatchers.IO) {
            localLocalDataSource.deleteUser(id)
        }
    }

    suspend fun updateUser(user: User) {
        withContext(Dispatchers.IO) {
            localLocalDataSource.updateUser(user)
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