package com.smcdeveloper.nobinoapp.data.remote

import com.smcdeveloper.nobinoapp.data.model.prducts.MovieResult

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
abstract class BaseApiResponse {

    suspend fun  safeApiCall(apiCall: suspend () -> Response<MovieResult>):NetworkResult2  =
        withContext(Dispatchers.IO) {
            try {
                val response = apiCall()
                if (response.isSuccessful) {
                    val body = response.body()
                    // Log.d(LOG_TAG,"SafeApiCall:"+body.toString())

                    body?.let {
                        // Log.d(LOG_TAG, "SafeApiCall...:$body")
                        return@withContext NetworkResult2.Success("",body)

                    }
                }
                return@withContext error("code : ${response.code()} , message : ${response.message()}")
            } catch (e: Exception) {
                return@withContext error(e.message ?: e.toString())
            }
















        }


    private fun  error(errorMessage: String): NetworkResult2 =
        NetworkResult2.Error("Api call failed : $errorMessage")









}