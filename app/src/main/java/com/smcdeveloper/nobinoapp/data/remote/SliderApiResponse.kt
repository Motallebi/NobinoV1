package com.smcdeveloper.nobinoapp.data.remote

import com.smcdeveloper.nobinoapp.data.model.sliders.Slider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
abstract class SliderApiResponse {
    suspend fun  safeApiCall(apiCall: suspend () -> Response<Slider>):SlidderResualt  =
        withContext(Dispatchers.IO) {
            try {
                val response = apiCall()
                if (response.isSuccessful) {
                    val body = response.body()
                   // Log.d(LOG_TAG,"SafeApiCall:"+body.toString())

                    body?.let {
                       // Log.d(LOG_TAG, "SafeApiCall...:$body")
                        return@withContext SlidderResualt.Success("",body)

                    }
                }
                return@withContext error("code : ${response.code()} , message : ${response.message()}")
            } catch (e: Exception) {
                return@withContext error(e.message ?: e.toString())
            }
        }


    private fun  error(errorMessage: String): SlidderResualt =
        SlidderResualt.Error("Api call failed : $errorMessage")









}