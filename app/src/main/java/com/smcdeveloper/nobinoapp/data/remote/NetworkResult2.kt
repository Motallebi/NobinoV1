package com.smcdeveloper.nobinoapp.data.remote

import com.smcdeveloper.nobinoapp.data.model.prducts.MovieResult

sealed class  NetworkResult2(
    val message: String? = null,
    val data: MovieResult? = null
) {
    class Success(message: String, data:MovieResult) : NetworkResult2(message,data)
    class Error(message: String, data: MovieResult? = null) : NetworkResult2(message, data)
    class Loading : NetworkResult2()
}
