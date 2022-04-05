package com.example.quiz4

import android.app.Application
import com.example.quiz4.di.ServiceLocator

class App: Application() {

    lateinit var serviceLocator: ServiceLocator

    override fun onCreate() {
        super.onCreate()
        serviceLocator = ServiceLocator(this)
    }

}