package com.smcdeveloper.nobinoapp.data.remote

import com.smcdeveloper.nobinoapp.data.model.prducts.MovieResult

sealed class NetworkResult<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T, message: String = "") : NetworkResult<T>(data, message)
    class Error<T>(message: String) : NetworkResult<T>(null, message)
    class Loading<T> : NetworkResult<T>()
}


