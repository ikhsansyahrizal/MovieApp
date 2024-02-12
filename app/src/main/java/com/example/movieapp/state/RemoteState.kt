package com.example.movieapp.state

sealed class RemoteState<out T: Any> {

    class Success<out T: Any>(val data: T): RemoteState<T>()

    class Error(val error: String): RemoteState<Nothing>()

}