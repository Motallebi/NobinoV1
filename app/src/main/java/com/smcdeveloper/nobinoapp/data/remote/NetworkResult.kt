package com.smcdeveloper.nobinoapp.data.remote

import com.smcdeveloper.nobinoapp.data.model.prducts.MovieResult

sealed class  NetworkResult(
    val message: String? = null,
    val data: MovieResult? = null
) {
    class Success(message: String, data:MovieResult) : NetworkResult(message,data)
    class Error(message: String, data: MovieResult? = null) : NetworkResult(message, data)
    class Loading : NetworkResult()
}

