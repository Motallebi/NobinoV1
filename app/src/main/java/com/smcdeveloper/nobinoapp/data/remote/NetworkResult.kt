package com.smcdeveloper.nobinoapp.data.remote

import com.smcdeveloper.nobinoapp.data.model.prducts.MovieResult
import okhttp3.ResponseBody

sealed class NetworkResult<T>(
    val message: String? = null,
    val data: T? = null,
    val code:Int?=null

) {
    class Success<T>(data: T?, message: String = "",code: Int?=0) : NetworkResult<T>(message, data,code)
    class Error<T>(message: String,code: Int?=0) : NetworkResult<T>(message,code=code)
    class Loading<T> : NetworkResult<T>()
}

