package com.example.quiz4.data

import com.example.quiz4.data.local.IDataSource
import com.example.quiz4.data.local.model.User
import com.example.quiz4.data.local.model.UserWithHobbies
import com.example.quiz4.data.remote.model.UserInfo
import com.example.quiz4.data.remote.model.UsersListItem
import com.example.quiz4.util.Mapper
import com.example.quiz4.util.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import retrofit2.Response

class UserRepository(
    private val remoteDataSource: DataSource,
    private val localDataSource: IDataSource
) {

    suspend fun getUsers(): Flow<Result<List<UsersListItem?>>> {
        return flow {
            try {
                emit(Result.Loading())
                val response = remoteDataSource.getUsers()
                if (response.isSuccessful) {
                    response.body()?.let {
                        emit(Result.Success(it))
                    }
                } else {
                    emit(Result.Error("something went wrong..."))
                }
            } catch (e: Exception) {
                emit(Result.Error("The network is currently unable to respond"))
            }
        }
    }

    suspend fun showInfoUser(id: String): Flow<Result<UsersListItem?>> {
        return flow {
            try {
                emit(Result.Loading())
                val response = remoteDataSource.showInfo(id)
                if (response.isSuccessful) {
                    response.body()?.let {
                        emit(Result.Success(it))
                    }
                } else {
                    emit(Result.Error("User Not Found"))
                }
            } catch (e: Exception) {
                emit(Result.Error("The network is currently unable to respond"))
            }
        }
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

    suspend fun updateUser(user: User) {
        withContext(Dispatchers.IO) {
            localDataSource.updateUser(user)
        }
    }
}