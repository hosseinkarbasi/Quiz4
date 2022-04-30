package com.example.quiz4.data.repository

import com.example.quiz4.data.local.LocalDataSource
import com.example.quiz4.data.local.model.User
import com.example.quiz4.data.local.model.UserWithHobbies
import com.example.quiz4.data.remote.RemoteDataSource
import com.example.quiz4.data.remote.model.UserInfo
import com.example.quiz4.data.remote.model.UsersListItem
import com.example.quiz4.di.IoDispatcher
import com.example.quiz4.util.Mapper
import com.example.quiz4.util.Result
import com.example.quiz4.util.requestFlow
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    @IoDispatcher
    private val dispatcher: CoroutineDispatcher,
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,

    ) {

    suspend fun getUsers(): Flow<Result<List<UsersListItem>>> {
        return requestFlow(dispatcher) { remoteDataSource.getUsers() }
    }

    suspend fun showInfoUser(id: String): Flow<Result<UsersListItem>> {
        return requestFlow(dispatcher) { remoteDataSource.getShowInfo(id) }
    }

    suspend fun createUser(user: UserInfo): Response<String> {
        return remoteDataSource.createAccount(user)
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
        return localDataSource.gerUsers()
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

}