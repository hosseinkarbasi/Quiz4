package com.example.quiz4.util

sealed class Result<out R>(
    val data: R? = null,
    val message: String? = null
) {
    class Success<out T>(data: T) : Result<T>(data)
    class Loading<out T>(data: T? = null) : Result<T>(data)
    class Error<out T>(throwable: Throwable, data: T? = null) : Result<T>(data, throwable.message)
}