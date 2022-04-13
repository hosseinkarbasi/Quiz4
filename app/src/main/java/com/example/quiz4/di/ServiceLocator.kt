package com.example.quiz4.di

import android.app.Application
import com.example.quiz4.data.UserRepository
import com.example.quiz4.data.local.LocalDataSource
import com.example.quiz4.data.local.db.AppDataBase
import com.example.quiz4.data.remote.RemoteDataSource
import com.example.quiz4.data.remote.network.NetworkManager

class ServiceLocator(application: Application) {

    private val remoteDataSource = RemoteDataSource(NetworkManager.service)
    private val localDataSource = LocalDataSource(AppDataBase.getDatabase(application).movieDao())

    val userRepository = UserRepository(remoteDataSource,localDataSource)
}