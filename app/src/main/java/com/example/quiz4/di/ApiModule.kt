package com.example.quiz4.di

import com.example.quiz4.data.remote.network.UserApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @IoDispatcher
    fun provideDispatchers(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @Singleton
    fun client(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .addInterceptor { chain ->
                val request =
                    chain.request()
                        .newBuilder()
                        .addHeader("Authorization", "Bearer karbasi021iii")
                        .build()
                chain.proceed(request)
            }
            .build()
    }

    @Provides
    @Singleton
    fun retrofit(
        client: OkHttpClient,
    ): UserApi {
        return Retrofit.Builder()
            .baseUrl("http://papp.ir/api/v1/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UserApi::class.java)
    }
}