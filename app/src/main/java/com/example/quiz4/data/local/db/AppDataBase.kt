package com.example.quiz4.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.quiz4.data.local.model.Hobie
import com.example.quiz4.data.local.model.User

@Database(entities = [User::class, Hobie::class], version = 1, exportSchema = true)
abstract class AppDataBase : RoomDatabase() {
    abstract fun UserDao(): UserDao
}