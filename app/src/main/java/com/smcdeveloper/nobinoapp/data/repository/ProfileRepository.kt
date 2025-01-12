package com.smcdeveloper.nobinoapp.data.repository

import android.util.Log
import com.smcdeveloper.nobinoapp.data.model.profile.LoginResponse
import com.smcdeveloper.nobinoapp.data.remote.BaseApiResponse2
import com.smcdeveloper.nobinoapp.data.remote.NetworkResult
import com.smcdeveloper.nobinoapp.data.remote.ProfileApiInterface
import com.smcdeveloper.nobinoapp.util.Constants.NOBINO_LOG_TAG
import javax.inject.Inject

class ProfileRepository @Inject constructor(private val api: ProfileApiInterface): BaseApiResponse2() {


    suspend fun getOtp(mobile: String): NetworkResult<LoginResponse> {
        Log.d("Repository", "Request: mobile=$mobile")
        return safeApiCall {
            val response = api.getOtp(mobile = mobile)
            Log.d("Repository", "Response: ${response.body().toString()}")
            Log.d("API Response", "Raw Response: ${response.raw()}")
            Log.d("API Response", "Response Body: ${response.body()}")



            response
        }
    }

   suspend fun validateOtp(ref_number:String, otp:String, mobile: String,token:String): NetworkResult<LoginResponse> =

       safeApiCall {

           api.validateOtp(ref_number =ref_number , otp = otp, mobile = mobile )


       }









}

























































































































