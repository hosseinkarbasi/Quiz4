package com.example.quiz4.data.local.db

import androidx.room.*
import com.example.quiz4.data.local.model.Hobie
import com.example.quiz4.data.local.model.User
import com.example.quiz4.data.local.model.UserWithHobbies
import com.example.quiz4.data.remote.model.UsersListItem
import kotlinx.coroutines.flow.MutableStateFlow
import retrofit2.http.DELETE

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(user: User)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertHobbies(hobbies: List<Hobie>)

    @Query("SELECT * FROM user ")
    suspend fun getUsers(): List<UserWithHobbies>

    @Query("DELETE FROM user WHERE id=:id")
    suspend fun deleteUser(id: String)

    @Update
    fun updateUser(user: User)

    @Update
    fun updateHobbies(hobbies: List<Hobie>)
}