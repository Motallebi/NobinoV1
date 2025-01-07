package com.smcdeveloper.nobinoapp.data.remote

import com.smcdeveloper.nobinoapp.data.model.GeneralResponseResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

abstract class GeneralBaseApiResponse {

    suspend fun <T> safeApiCall(apiCall: suspend () -> Response<GeneralResponseResult<T>>): NetworkResult<T> =
        withContext(Dispatchers.IO) {
            try {
                val response = apiCall()
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        return@withContext NetworkResult.Success(data = body.data, message = body.message)
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
