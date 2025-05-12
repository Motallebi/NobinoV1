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
                    Log.d("SAFE_API_CALL", "API call successful. Response: ${response.body()}")
                    val body = response.body()
                    if (body != null) {
                        Log.d("SafeApiCall", "Response Body: $body")
                        return@withContext NetworkResult.Success(data =response.body(), code = response.code())
                    }
                    else {
                        return@withContext NetworkResult.Error("Response body is null.", code = response.code())
                      //  return@withContext NetworkResult.Error(message = "",response.errorBody())
                    }
                }
                else {


                    Log.e("SAFE_API_CALL", "API call failed. Code: ${response.code()}, Message: ${response.message()}")
                    return@withContext NetworkResult.Error(message = "Code: ${response.code()}, Message: ${response.message()}", code = response.code()  )
                }
            } catch (e: Exception) {
                return@withContext NetworkResult.Error("API call failed: ${e.message ?: e.toString()}", code = 1000)
            }
        }
}
