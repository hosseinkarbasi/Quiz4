package com.example.quiz4.di

import android.content.Context
import androidx.room.Room
import com.example.quiz4.data.local.db.AppDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AppDataBase = Room.databaseBuilder(
        context,
        AppDataBase::class.java,
        "users_database"
    ).fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun provideDao(
        db: AppDataBase
    ) = db.UserDao()

}