package com.smcdeveloper.nobinoapp.data.remote

import com.smcdeveloper.nobinoapp.data.model.prducts.MovieResult

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
abstract class BaseGenericApiResponse {


    // A generic safe API call function
    suspend fun <T> safeApiCall(
        apiCall: suspend () -> Response<T>,
        parseResponse: (T?) -> T? = { it } // Optional lambda for custom parsing
    ): NetworkResult<T> =
        withContext(Dispatchers.IO) {
            try {
                val response = apiCall()
                if (response.isSuccessful) {
                    val body = parseResponse(response.body())
                    if (body != null) {
                        return@withContext NetworkResult.Success(data = body, message = "Success")
                    } else {
                        return@withContext NetworkResult.Error(message = "Response body is null.")
                    }
                } else {
                    return@withContext NetworkResult.Error(
                        message = "Error: ${response.code()} - ${response.message()}"
                    )
                }
            } catch (e: Exception) {
                return@withContext NetworkResult.Error(
                    message = "API call failed: ${e.message ?: e.toString()}"
                )
            }
        }




}