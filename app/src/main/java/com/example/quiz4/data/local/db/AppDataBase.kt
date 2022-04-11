package com.example.quiz4.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.quiz4.data.local.model.Hobie
import com.example.quiz4.data.local.model.User

@Database(entities = [User::class, Hobie::class], version = 1, exportSchema = true)
abstract class AppDataBase : RoomDatabase() {
    abstract fun movieDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: AppDataBase? = null
        fun getDatabase(context: Context): AppDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDataBase::class.java,
                    "users_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}