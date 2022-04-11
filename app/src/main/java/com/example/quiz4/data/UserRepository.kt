package com.example.quiz4.data

import com.example.quiz4.data.local.IDataSource
import com.example.quiz4.data.local.model.User
import com.example.quiz4.data.local.model.UserWithHobbies
import com.example.quiz4.data.remote.model.UserInfo
import com.example.quiz4.data.remote.model.UsersListItem
import com.example.quiz4.util.Mapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class UserRepository(
    private val remoteDataSource: DataSource,
    private val localDataSource: IDataSource
) {

    suspend fun getUsers(): Response<List<UsersListItem>> {
        val data: Response<List<UsersListItem>>
        withContext(Dispatchers.IO) {
            data = remoteDataSource.getUsers()
        }
        return data
    }

    suspend fun showInfoUser(id: String): Response<UsersListItem> {
        val data: Response<UsersListItem>
        withContext(Dispatchers.IO) {
            data = remoteDataSource.showInfo(id)
        }
        return data
    }

    suspend fun createUser(user: UserInfo): Response<String> {
        val data: Response<String>
        withContext(Dispatchers.IO) {
            data = remoteDataSource.createUser(user)
        }
        return data
    }

    suspend fun insertUser(user: User) {
        withContext(Dispatchers.IO) {
            localDataSource.insertUser(user)
        }
    }

    suspend fun insertHobbies(id: String) {
        withContext(Dispatchers.IO) {
            val user = remoteDataSource.showInfo(id)
            val hobbies = Mapper.transformToHobie(user.body()!!)
            localDataSource.insertHobbies(hobbies)
        }
    }

    suspend fun getUsersFromDataBase(): List<UserWithHobbies> {
        return localDataSource.getUsers()
    }

    suspend fun deleteUser(id: String) {
        withContext(Dispatchers.IO) {
            localDataSource.deleteUser(id)
        }
    }

    suspend fun updateUser(user: User){
        withContext(Dispatchers.IO){
            localDataSource.updateUser(user)
        }
    }
}