package com.smcdeveloper.nobinoapp.data.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.smcdeveloper.nobinoapp.data.model.avatar.Avatar
import com.smcdeveloper.nobinoapp.data.model.payment.PaymentHistory
import com.smcdeveloper.nobinoapp.data.model.payment.PaymentRequest
import com.smcdeveloper.nobinoapp.data.model.payment.PaymentResponse
import com.smcdeveloper.nobinoapp.data.model.profile.ActiveUserProfile
import com.smcdeveloper.nobinoapp.data.model.profile.LoginResponse
import com.smcdeveloper.nobinoapp.data.model.profile.NewUserProfileRequest
import com.smcdeveloper.nobinoapp.data.model.profile.ProfileActivationRequest
import com.smcdeveloper.nobinoapp.data.model.profile.ProfileResponse
import com.smcdeveloper.nobinoapp.data.model.profile.UpdateUserProfileRequest
import com.smcdeveloper.nobinoapp.data.model.profile.UserInfo
import com.smcdeveloper.nobinoapp.data.remote.BaseApiResponse2
import com.smcdeveloper.nobinoapp.data.remote.NetworkResult
import com.smcdeveloper.nobinoapp.data.remote.ProfileApiInterface
import com.smcdeveloper.nobinoapp.data.source.PaymentDataSource
import com.smcdeveloper.nobinoapp.util.DigitHelper
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProfileRepository @Inject constructor(private val api: ProfileApiInterface): BaseApiResponse2() {


    suspend fun getOtp(mobile: String): NetworkResult<LoginResponse> {
        Log.d("Repository", "Request: mobile=$mobile")
        return safeApiCall {
            val response = api.getOtp(mobile = DigitHelper.digitByLocateFaToEn(mobile))
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
        Log.d("Repository", "Request: mobile_num=${DigitHelper.digitByLocateFaToEn(mobile)}")
        //Log.d("Repository", "Request: ref_num=$ref_number")

        return safeApiCall {
            val response = api.validateOtp(refNumber = ref_number, otp = otp, mobile = DigitHelper.digitByLocateFaToEn(mobile))
            Log.d("Repository", "Request: ref_num=${response.body().toString()}")



            response


        }


    }





suspend fun getUserProfile(auth:String) :NetworkResult<UserInfo> {

    val result = safeApiCall {


        api.getUserInfo()


    }

    Log.d("user","Api call")


    return result
}

    suspend fun  getUserPaymentHistory1(auth: String):NetworkResult<PaymentHistory> =

        safeApiCall {

            api.getUserPaymentHistory("1","10")

        }


    fun getPaymentHistory(): Flow<PagingData<PaymentHistory.PaymentHistoryData.Payment>> {
        return Pager(
            config = PagingConfig(pageSize = 10,
                initialLoadSize = 10


            ),
            pagingSourceFactory = {
                PaymentDataSource( api = api,







                    )
            }
        ).flow
    }
















  suspend fun getAvatars() :NetworkResult<Avatar> =

      safeApiCall {

          api.getAvatars()



      }











    suspend fun updateUserProfile(updateRequest:UpdateUserProfileRequest): NetworkResult<UserInfo> =

        safeApiCall {

            api.updateUserInfo(updateRequest)


        }




    suspend fun makeNewProfile(auth: String,profileRequest: NewUserProfileRequest ) :NetworkResult<ProfileResponse> =

        safeApiCall {

            api.makeNewProfile(auth = auth ,profileRequest )



        }



    suspend fun activeNewProfile(auth: String,profileActivationRequest: ProfileActivationRequest ) :NetworkResult<ProfileResponse> =

        safeApiCall {

            api.profileActivation(profileActivationRequest )



        }



    suspend fun activeCurrentProfile(profileActivationRequest: ProfileActivationRequest ) :NetworkResult<ProfileResponse> =

        safeApiCall {

            api.activeCurrentProfile(profileActivationRequest )



        }
















    suspend fun getCurrentUserProfile() : NetworkResult<List<ActiveUserProfile>> =

        safeApiCall {

            api.getUserProfile()


        }

















    suspend fun sendUserPayment(paymentRequest: PaymentRequest): NetworkResult<PaymentResponse> =

        safeApiCall {

            api.postUserPayment(paymentRequest)


        }











}





























































































































