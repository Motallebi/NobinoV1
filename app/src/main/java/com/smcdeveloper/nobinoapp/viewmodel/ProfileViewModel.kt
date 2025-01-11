package com.smcdeveloper.nobinoapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smcdeveloper.nobinoapp.data.model.profile.LoginResponse
import com.smcdeveloper.nobinoapp.data.remote.NetworkResult
import com.smcdeveloper.nobinoapp.data.repository.ProfileRepository
import com.smcdeveloper.nobinoapp.ui.screens.profile.ProfileScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val
                                           repository: ProfileRepository):ViewModel() {


    //Shared View Model
   // var screenState by mutableStateOf(ProfileScreenState.LOGIN_STATE)
    var inputPhoneState by mutableStateOf("")
    var inputRefSates by mutableStateOf("")
    var inputOtpState by mutableStateOf("")
    var loadingState by mutableStateOf(false)
    var refNumberStat by mutableStateOf("")

    private val _currentState = MutableStateFlow(ProfileScreenState.LOGIN_STATE
    )
    val currentState: StateFlow<ProfileScreenState> = _currentState












   // var OtpState by  MutableStateFlow("")


    val loginResponse = MutableStateFlow<NetworkResult<LoginResponse>>(NetworkResult.Loading())

    val setUserNameResponse = MutableStateFlow<NetworkResult<String>>(NetworkResult.Loading())


  //  var currentState: ProfileScreenState = ProfileScreenState.LOGIN_STATE


    fun updateState(newState: ProfileScreenState) {
        viewModelScope.launch {
            _currentState.emit(newState)
        }
    }

















    fun setOtpValue()
    {
       //OtpState ="88998"

        viewModelScope.launch {





       }



    }





    fun getOtp()
    {

        viewModelScope.launch {
            loadingState = true
           // val loginRequest = LoginRequest(client_id = "nobino-direct", mobile = inputPhoneState, grant_type = "password")
            loginResponse.emit(repository.GetOtp(mobile = inputPhoneState))
        }






    }

    fun validateOtp()
    {

        viewModelScope.launch {
            loadingState = true
           // val loginRequest = LoginRequest(client_id = "nobino-direct", mobile = inputPhoneState, grant_type = "password",)
            loginResponse.emit(repository.validateOtp(
                ref_number = inputRefSates,
                otp = inputOtpState,
                mobile = inputPhoneState,
                token = ""
            ))
        }


        loadingState=false






    }










 }
















































