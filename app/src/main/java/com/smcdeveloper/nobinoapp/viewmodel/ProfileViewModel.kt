package com.smcdeveloper.nobinoapp.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smcdeveloper.nobinoapp.data.model.profile.LoginResponse
import com.smcdeveloper.nobinoapp.data.model.profile.UpdateUserProfileRequest
import com.smcdeveloper.nobinoapp.data.model.profile.UserInfo
import com.smcdeveloper.nobinoapp.data.remote.NetworkResult
import com.smcdeveloper.nobinoapp.data.repository.ProfileRepository
import com.smcdeveloper.nobinoapp.ui.screens.profile.ProfileScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: ProfileRepository



):ViewModel() {


    //Shared View Model
    // var screenState by mutableStateOf(ProfileScreenState.LOGIN_STATE)
    var inputPhoneState by mutableStateOf("")
    var inputRefSates by mutableStateOf("")
    var inputOtpState by mutableStateOf("")
    var loadingState by mutableStateOf(false)
    var refNumberStat by mutableStateOf("")

    private val _currentState = MutableStateFlow(
        ProfileScreenState.LOGIN_STATE
    )
    val currentState: StateFlow<ProfileScreenState> = _currentState


    // var OtpState by  MutableStateFlow("")


    val loginResponse = MutableStateFlow<NetworkResult<LoginResponse>>(NetworkResult.Loading())

    val setUserNameResponse = MutableStateFlow<NetworkResult<String>>(NetworkResult.Loading())

  //  private val _userProfile = MutableStateFlow<UserInfo?>(null)
  //  val userProfile: StateFlow<UserInfo?> = _userProfile


    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading




    private val _userProfile = MutableStateFlow<NetworkResult<UserInfo>>(NetworkResult.Loading())
    val userProfile: StateFlow<NetworkResult<UserInfo>> get() = _userProfile.asStateFlow()


    //  var currentState: ProfileScreenState = ProfileScreenState.LOGIN_STATE


    fun updateState(newState: ProfileScreenState) {
        viewModelScope.launch {
            _currentState.emit(newState)
        }
    }


    fun setOtpValue() {
        //OtpState ="88998"

        viewModelScope.launch {


        }


    }


    fun getOtp() {

        viewModelScope.launch {
            loadingState = true
            val result = repository.getOtp(mobile = inputPhoneState)
            loginResponse.emit(result)
            if (result is NetworkResult.Success) {
                inputRefSates = result.data?.refNumber ?: ""
            }


            // val loginRequest = LoginRequest(client_id = "nobino-direct", mobile = inputPhoneState, grant_type = "password")
            //loginResponse.emit(repository.getOtp(mobile = inputPhoneState))

        }

        loadingState = false


    }

    fun validateOtp(
        refNumber: String,
        otp: String,
        mobile: String
    ) {


        Log.d("valdiate", "ref num is $refNumber otp is $otp mobile is $mobile")


        viewModelScope.launch {
            loadingState = true
            val result = repository.validateOtp(
                ref_number = refNumber,
                otp = otp,
                mobile = mobile,
                token = ""


            )



            loginResponse.emit(result)

            if (result is NetworkResult.Success) {
                val token = result.data?.access_token
                // Handle token if needed
            }
            loadingState = false
        }
    }

    fun requestNewOtp() {
        TODO("Not yet implemented")
    }



    fun fetchUserProfile(auth: String) {
        viewModelScope.launch {
            _userProfile.value = NetworkResult.Loading() // Show loading state
            _userProfile.value = repository.getUserProfile(auth) // Fetch data and update state
        }
    }










    private fun updateUserInfo(userId: String, updateRequest: UpdateUserProfileRequest) {

        viewModelScope.launch {


            repository.UpdateUserProfile(userId, updateRequest)


        }


    }


}


















































