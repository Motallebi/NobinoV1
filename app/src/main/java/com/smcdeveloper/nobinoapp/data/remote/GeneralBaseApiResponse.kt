package com.smcdeveloper.nobinoapp.data.remote

import com.smcdeveloper.nobinoapp.data.model.ResponseResultWithSingleObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

abstract class GeneralBaseApiResponse {

    suspend fun <T, R> safeApiCall(
        apiCall: suspend () -> Response<R>,
        parseResponse: (R?) -> T? = { it as? T } // Default: Directly cast R to T if possible
    ): NetworkResult<T> =
        withContext(Dispatchers.IO) {
            try {
                val response = apiCall()
                if (response.isSuccessful) {
                    val body = response.body()
                    val parsedData = parseResponse(body)
                    if (parsedData != null) {
                        if (body is ResponseResultWithSingleObject<*>) {
                            return@withContext NetworkResult.Success(
                                data = parsedData,
                                message = body.message ?: "Success"
                            )
                        }
                        return@withContext NetworkResult.Success(data = parsedData, message = "Success")
                    } else {
                        return@withContext NetworkResult.Error("Parsed data is null.")
                    }
                } else {
                    return@withContext NetworkResult.Error("Code: ${response.code()}, Message: ${response.message()}")
                }
            } catch (e: Exception) {
                return@withContext NetworkResult.Error("API call failed: ${e.message ?: e.toString()}")
            }
        }

}
