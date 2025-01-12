package com.smcdeveloper.nobinoapp.data.remote

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

abstract class BaseApiResponse2 {

    suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): NetworkResult<T> =
        withContext(Dispatchers.IO) {
            try {
                val response = apiCall()
                Log.d("SafeApiCall", "Response Code: ${response.code()}, Message: ${response.message()}")
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        Log.d("SafeApiCall", "Response Body: $body")
                        return@withContext NetworkResult.Success(body)
                    } else {
                        return@withContext NetworkResult.Error("Response body is null.")
                    }
                } else {
                    return@withContext NetworkResult.Error("Code: ${response.code()}, Message: ${response.message()}")
                }
            } catch (e: Exception) {
                return@withContext NetworkResult.Error("API call failed: ${e.message ?: e.toString()}")
            }
        }
}
