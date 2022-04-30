package com.example.quiz4.data.local.db

import androidx.room.*
import com.example.quiz4.data.local.model.Hobie
import com.example.quiz4.data.local.model.User
import com.example.quiz4.data.local.model.UserWithHobbies
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(user: User)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertHobbies(hobbies: List<Hobie>)

    @Query("SELECT * FROM user ")
    fun getUsers(): Flow<List<UserWithHobbies>>

    @Query("DELETE FROM user WHERE id=:id")
    suspend fun deleteUser(id: String)

    @Update
    suspend fun updateUser(user: User)
}