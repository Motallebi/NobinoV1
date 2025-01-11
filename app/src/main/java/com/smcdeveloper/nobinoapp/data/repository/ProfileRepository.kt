package com.smcdeveloper.nobinoapp.data.repository

import com.smcdeveloper.nobinoapp.data.model.profile.LoginResponse
import com.smcdeveloper.nobinoapp.data.remote.BaseApiResponse2
import com.smcdeveloper.nobinoapp.data.remote.NetworkResult
import com.smcdeveloper.nobinoapp.data.remote.ProfileApiInterface
import javax.inject.Inject

class ProfileRepository @Inject constructor(private val api: ProfileApiInterface): BaseApiResponse2() {


    suspend fun GetOtp(mobile:String): NetworkResult<LoginResponse> =
        safeApiCall {

            api.GetOtp(mobile =mobile)

        }

   suspend fun validateOtp(ref_number:String, otp:String, mobile: String,token:String): NetworkResult<LoginResponse> =

       safeApiCall {

           api.validateOtp(ref_number =ref_number , otp = otp, mobile = mobile )


       }









}

























































































































