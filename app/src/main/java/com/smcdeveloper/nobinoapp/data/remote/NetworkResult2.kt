package com.smcdeveloper.nobinoapp.data.remote

import com.smcdeveloper.nobinoapp.data.model.prducts.MovieResult

sealed class NetworkResult2(
    val data: T? = null,
    val message: String? = null
) {
    class Success(data:, message: String = "") : NetworkResult2<T>(data, message)
    class Error(message: String) : NetworkResult2<T>(null, message)
    class Loading : NetworkResult2<T>()
}


