package com.smcdeveloper.nobinoapp.data.remote

import com.smcdeveloper.nobinoapp.data.model.prducts.MovieResult

sealed class NetworkResult<T>(
    val message: String? = null,
    val data: T? = null,

) {
    class Success<T>(data: T?, message: String = "") : NetworkResult<T>(message, data)
    class Error<T>(message: String) : NetworkResult<T>(message,data = null)
    class Loading<T> : NetworkResult<T>()
}

