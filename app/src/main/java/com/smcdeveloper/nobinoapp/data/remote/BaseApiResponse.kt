package com.smcdeveloper.nobinoapp.data.remote

import android.util.Log
import com.smcdeveloper.nobinoapp.data.model.ResponseResult
import com.smcdeveloper.nobinoapp.data.model.bb.MovieResult
import com.smcdeveloper.nobinoapp.data.model.testrest.PostResponseModel
import com.smcdeveloper.nobinoapp.util.Constants.LOG_TAG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
abstract class BaseApiResponse {
    suspend fun  safeApiCall(apiCall: suspend () -> Response<MovieResult>):NetworkResult  =
        withContext(Dispatchers.IO) {
            try {
                val response = apiCall()
                if (response.isSuccessful) {
                    val body = response.body()
                   // Log.d(LOG_TAG,"SafeApiCall:"+body.toString())

                    body?.let {
                       // Log.d(LOG_TAG, "SafeApiCall...:$body")
                        return@withContext NetworkResult.Success("",body)

                    }
                }
                return@withContext error("code : ${response.code()} , message : ${response.message()}")
            } catch (e: Exception) {
                return@withContext error(e.message ?: e.toString())
            }
        }


    private fun  error(errorMessage: String): NetworkResult =
        NetworkResult.Error("Api call failed : $errorMessage")









}