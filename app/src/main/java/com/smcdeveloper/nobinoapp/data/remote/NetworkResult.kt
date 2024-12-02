package com.smcdeveloper.nobinoapp.data.remote

import com.smcdeveloper.nobinoapp.data.model.bb.MovieResult
import com.smcdeveloper.nobinoapp.data.model.nn.MoviesData
import com.smcdeveloper.nobinoapp.data.model.testrest.PostResponseModel

sealed class  NetworkResult(
    val message: String? = null,
    val data: List<PostResponseModel>? = null
) {
    class Success(message: String, data:List<PostResponseModel>) : NetworkResult(message,data)
    class Error(message: String, data: List<PostResponseModel>? = null) : NetworkResult(message, data)
    class Loading : NetworkResult()
}

