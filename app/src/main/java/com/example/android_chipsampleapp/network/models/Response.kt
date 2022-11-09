package com.example.android_chipsampleapp.network.models

sealed class Response<T : Any?> {
    class Success<T : Any?>(val data: T, status: String) : Response<T>()
    class Error<T : Any?>(val data: T?, status: String) : Response<T>()
    class Loading<T : Any?>(val data: T) : Response<T>()
}