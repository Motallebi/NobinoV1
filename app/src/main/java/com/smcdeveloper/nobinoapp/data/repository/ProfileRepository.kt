package com.smcdeveloper.nobinoapp.data.repository

import android.util.Log
import com.smcdeveloper.nobinoapp.data.model.profile.LoginResponse
import com.smcdeveloper.nobinoapp.data.model.profile.UpdateUserProfileRequest
import com.smcdeveloper.nobinoapp.data.model.profile.UserInfo
import com.smcdeveloper.nobinoapp.data.remote.BaseApiResponse2
import com.smcdeveloper.nobinoapp.data.remote.NetworkResult
import com.smcdeveloper.nobinoapp.data.remote.ProfileApiInterface
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

    suspend fun validateOtp(
        ref_number: String,
        otp: String,
        mobile: String,
        token: String
    ): NetworkResult<LoginResponse> {

        Log.d("Repository", "Request: ref_num=$ref_number")
        //Log.d("Repository", "Request: ref_num=$ref_number")

        return safeApiCall {
            val response = api.validateOtp(refNumber = ref_number, otp = otp, mobile = mobile)
            Log.d("Repository", "Request: ref_num=${response.body().toString()}")



            response


        }


    }





suspend fun getUserProfile(auth:String) :NetworkResult<UserInfo> {

    val result = safeApiCall {


        api.getUserInfo(auth)


    }

    Log.d("user","Api call")


    return result
}


    suspend fun UpdateUserProfile(userid:String,updateRequest:UpdateUserProfileRequest): NetworkResult<UserInfo> =

        safeApiCall {

            api.updateUserInfo(userid,updateRequest)


        }










}





























































































































