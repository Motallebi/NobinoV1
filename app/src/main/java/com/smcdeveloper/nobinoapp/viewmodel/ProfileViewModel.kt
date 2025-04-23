package com.smcdeveloper.nobinoapp.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import com.smcdeveloper.nobinoapp.data.model.avatar.Avatar
import com.smcdeveloper.nobinoapp.data.model.payment.PaymentHistory
import com.smcdeveloper.nobinoapp.data.model.payment.PaymentRequest
import com.smcdeveloper.nobinoapp.data.model.payment.PaymentResponse
import com.smcdeveloper.nobinoapp.data.model.prducts.MovieResult
import com.smcdeveloper.nobinoapp.data.model.profile.ActiveUserProfile
import com.smcdeveloper.nobinoapp.data.model.profile.LoginResponse
import com.smcdeveloper.nobinoapp.data.model.profile.NewUserProfileRequest
import com.smcdeveloper.nobinoapp.data.model.profile.ProfileResponse
import com.smcdeveloper.nobinoapp.data.model.profile.UpdateUserProfileRequest
import com.smcdeveloper.nobinoapp.data.model.profile.UserInfo
import com.smcdeveloper.nobinoapp.data.remote.NetworkResult
import com.smcdeveloper.nobinoapp.data.repository.ProfileRepository
import com.smcdeveloper.nobinoapp.data.source.PaymentDataSource
import com.smcdeveloper.nobinoapp.data.source.SearchDataSource1
import com.smcdeveloper.nobinoapp.ui.screens.profile.ProfileScreenState
import com.smcdeveloper.nobinoapp.ui.screens.profile.ValidationStatus
import com.smcdeveloper.nobinoapp.util.AES
import com.smcdeveloper.nobinoapp.util.Constants.USER_TOKEN
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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
    private val _otpCode = mutableStateOf("")
    val otpCode: State<String> = _otpCode

    private val _requestId = mutableStateOf<String?>(null)
    val requestId: State<String?> = _requestId
    var status by  mutableStateOf<ValidationStatus>(ValidationStatus.Default)






    private val _profileState = MutableStateFlow<NetworkResult<ProfileResponse>>(NetworkResult.Loading())
    val profileState: StateFlow<NetworkResult<ProfileResponse>> = _profileState


//// PaymentHistoryFlow

    private val _paymentHistoryData = MutableStateFlow<PagingData<PaymentHistory.PaymentHistoryData.Payment>>(PagingData.empty())
    val paymentHistoryData = _paymentHistoryData













    private val _currentState = MutableStateFlow(
        ProfileScreenState.LOGIN_STATE
    )
    val currentState: StateFlow<ProfileScreenState> = _currentState


    // var OtpState by  MutableStateFlow("")


    val loginResponse = MutableStateFlow<NetworkResult<LoginResponse>>(NetworkResult.Loading())

    val setUserNameResponse = MutableStateFlow<NetworkResult<String>>(NetworkResult.Loading())
    val _paymentResponse = MutableStateFlow<NetworkResult<PaymentResponse>>(NetworkResult.Loading())
    val paymentResponse: StateFlow<NetworkResult<PaymentResponse>> get() = _paymentResponse.asStateFlow()


    val _avatar = MutableStateFlow<NetworkResult<Avatar>>(NetworkResult.Loading())
    val avatar : StateFlow<NetworkResult<Avatar>> get() = _avatar.asStateFlow()


    private val _imageBitmaps = MutableStateFlow<List<ImageBitmap>>(emptyList())
    val imageBitmaps: StateFlow<List<ImageBitmap>> = _imageBitmaps


    val _activeUserProfile = MutableStateFlow<NetworkResult<List<ActiveUserProfile>>>(NetworkResult.Loading())
    val activeUserProfile : StateFlow<NetworkResult<List<ActiveUserProfile>>> get() = _activeUserProfile.asStateFlow()














  //  private val _userProfile = MutableStateFlow<UserInfo?>(null)
  //  val userProfile: StateFlow<UserInfo?> = _userProfile


    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading








    private val _userProfile = MutableStateFlow<NetworkResult<UserInfo>>(NetworkResult.Loading())
    val userProfile: StateFlow<NetworkResult<UserInfo>> get() = _userProfile.asStateFlow()


    // Store profile details needed to complete creation
    private val _name = mutableStateOf("")
    private val _username = mutableStateOf("")
    private val _avatarId = mutableStateOf(1)


     var _name1 = mutableStateOf("")
     var _username1 = mutableStateOf("")


    init {
        Log.d("Profile", "ViewModel created")
        Log.d("profile", "initialize State.... name is ${_name.value} username is ${_username.value}")




    }



/// using ValidationFlow
private val _validationStatus = MutableStateFlow<ValidationStatus>(ValidationStatus.Default)
    val validationStatus: StateFlow<ValidationStatus> = _validationStatus

    fun setError() {
        _validationStatus.value = ValidationStatus.Error

        viewModelScope.launch {
            delay(2000)
            _validationStatus.value = ValidationStatus.Default
        }
    }
/////


    private val _validationEvents = MutableSharedFlow<ValidationStatus>()
    val validationEvents = _validationEvents.asSharedFlow()

    fun triggerError() {
        viewModelScope.launch {


                _validationEvents.emit(ValidationStatus.Error)
                _validationStatus.emit(ValidationStatus.Error)
                 Log.d("valid","Status is Error")

            delay(1000)
            _validationEvents.emit(ValidationStatus.Default)
            _validationStatus.emit(ValidationStatus.Default)
            Log.d("valid","Status is Default")
        }
    }






    fun setProfileData(name: String, username: String,avatarId:Int) {
       Log.d("profile","set profile......")
        _name.value = name
        _username.value = username
        _avatarId.value=avatarId
        Log.d("profile", "initialize State.... name is ${_name.value} username is ${_username.value}")
    }






















    //  var currentState: ProfileScreenState = ProfileScreenState.LOGIN_STATE


    fun updateState(newState: ProfileScreenState) {
        viewModelScope.launch {
            _currentState.emit(newState)
        }
    }






    fun setOtpCode(code: String) {
        _otpCode.value = code
    }





    fun getAvatars()
    {
        viewModelScope.launch {

            try{

           _isLoading.value=true

           val result= repository.getAvatars()
            _avatar.value=result









                  }
        catch (e: Exception) {
        // Handle error as needed.
        e.printStackTrace()
    }




        }

        _isLoading.value=false








    }



    fun makeNewProfile(auth: String,profileRequest2: NewUserProfileRequest,otp:String,name:String,username:String)
    {
        viewModelScope.launch {

           var profileRequest = NewUserProfileRequest(
               step = 0,
               age ="AGE_3_TO_10"



            )

            val data1 = async { repository.makeNewProfile(auth,profileRequest) }

            profileRequest = NewUserProfileRequest(

                step = 1,
                requestId = data1.await().data?.requestId,
                otpCode = otpCode.value





            )


            val data2 = async { repository.makeNewProfile(auth,profileRequest) }

            profileRequest = NewUserProfileRequest(

                step = 2,
                requestId = data1.await().data?.requestId,
                otpCode = otp,
                name = name,
                username = username






            )


            val response =  async { repository.makeNewProfile(auth,profileRequest) }

            val data1Response = data1.await()
            val data2Response =data2.await()
            val finalresult= response.await()

















        }






















    }
































    fun createProfileFlow(
        auth: String,
      //  otp: String,
        name: String,
        username: String
    ): Flow<NetworkResult<ProfileResponse>> = flow {
        // Emit initial loading state
        emit(NetworkResult.Loading())

        // Step 0: Create the initial profile (e.g., setting the age)
        val step0Request = NewUserProfileRequest(
            step = 0,
            age = "AGE_3_TO_10"
        )
        val step0Response = repository.makeNewProfile(auth, step0Request)
        if (step0Response is NetworkResult.Error) {
            emit(NetworkResult.Error("Step 0 error: ${step0Response.message ?: "Unknown error"}"))
            return@flow
        }

        // Step 1: Verify OTP using the requestId from step 0



            val step1Request = NewUserProfileRequest(
                step = 1,
                requestId = (step0Response as NetworkResult.Success).data?.requestId,
                otpCode = otpCode.value
            )



        val step1Response = repository.makeNewProfile(auth, step1Request)
        if (step1Response is NetworkResult.Error) {
            emit(NetworkResult.Error("Step 1 error: ${step1Response.message ?: "Unknown error"}"))
            return@flow
        }

        // Step 2: Finalize the profile with additional details (name, username)
        val step2Request = NewUserProfileRequest(
            step = 2,
            requestId = (step1Response as NetworkResult.Success).data?.requestId,
            otpCode = "",
            name = name,
            username = username
        )
        val step2Response = repository.makeNewProfile(auth, step2Request)
        if (step2Response is NetworkResult.Error) {
            emit(NetworkResult.Error("Step 2 error: ${step2Response.message ?: "Unknown error"}"))
            return@flow
        }

        // Emit final successful result
        emit(step2Response)
    }.flowOn(Dispatchers.IO) // Run network calls on the IO dispatcher



    fun  startProfileCreation(auth: String,  name: String, username: String) {
        viewModelScope.launch {
            createProfileFlow(auth, name, username)
                .collect { result ->
                    _profileState.value = result
                }
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
        mobile: String,
        onValidate:(ValidationStatus)->Unit
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
                status=ValidationStatus.Success
                onValidate(ValidationStatus.Success)
                _validationStatus.value=ValidationStatus.Success

                // Handle token if needed
            }
            loadingState = false
          //  status=ValidationStatus.Error
          //  onValidate(ValidationStatus.Error)
           // _validationStatus.value=ValidationStatus.Error
          //  _validationEvents.emit(ValidationStatus.Error)
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



    fun postPayment(paymentRequest: PaymentRequest) {

        viewModelScope.launch {


           _isLoading.value =true
          val result=  repository.sendUserPayment(paymentRequest)
            _paymentResponse.value=result


            _isLoading.value=false




        }


    }



    fun getPaymentHistory() {

        viewModelScope.launch {

            repository.getPaymentHistory()



                .cachedIn(viewModelScope).collect {
                _paymentHistoryData.value = it
                }





        }


    }























    /**
     * Initiates profile creation by performing step 0.
     * This call sends the OTP to the user and saves the request ID.
     */
    fun initiateProfileCreation(auth: String, name: String, username: String,avatarId: Int=1) {

        Log.d("profile","initialize State....")
        viewModelScope.launch {
            _profileState.value = NetworkResult.Loading()
            // Save profile data for later use in step 2
            Log.d("profile","initialize State.... name is $name user name is $username ")
          //  setProfileData(name, username)
            Log.d("profile", "initialize State.... name is ${_name.value} username is ${_username.value}")

          //Log.d("profile","initialize State.... name is $_name name is ${_username.value} ")



            val step0Request = NewUserProfileRequest(step = 0, age = "AGE_3_TO_10")
            val step0Response = repository.makeNewProfile(auth, step0Request)
            if (step0Response is NetworkResult.Success) {

                setProfileData(name,username,avatarId,)

                _requestId.value = step0Response.data?.requestId
                // You might want to update state here to show that OTP has been sent.
                Log.d("profile","requestid is....${requestId.value}" )
                _profileState.value = step0Response
            } else if (step0Response is NetworkResult.Error) {
                _profileState.value = step0Response
            }
        }

        Log.d("profile","requestid is....${requestId.value}" )
        Log.d("profile","end of initialize State...." )


    }

    /**
     * Completes the profile creation using the stored OTP.
     * This function performs steps 1 (OTP verification) and 2 (finalizing profile).
     */
    fun completeProfileCreation1(auth: String) {
        viewModelScope.launch {
            // Validate OTP locally (basic check)
            if (otpCode.value.isBlank() || otpCode.value.length != 5) {
                _profileState.value = NetworkResult.Error("Invalid OTP")
                return@launch
            }
            val currentRequestId = requestId.value
            if (currentRequestId == null) {
                _profileState.value = NetworkResult.Error("Missing request ID. Please initiate profile creation first.")
                return@launch
            }
            _profileState.value = NetworkResult.Loading()
            // Step 1: Verify OTP using the stored requestId and entered OTP
            val step1Request = NewUserProfileRequest(
                step = 1,
                requestId = currentRequestId,
                otpCode = otpCode.value
            )
            val step1Response = repository.makeNewProfile(auth, step1Request)
            if (step1Response is NetworkResult.Error) {
                _profileState.value = NetworkResult.Error("OTP Verification Error: ${step1Response.message}")
                return@launch
            }
            // Step 2: Finalize profile creation with additional details
            val step2Request = NewUserProfileRequest(
                step = 2,
                requestId = (step1Response as NetworkResult.Success).data?.requestId,
                otpCode = otpCode.value,
                name = _name.value,
                username = _username.value
            )
            val step2Response = repository.makeNewProfile(auth, step2Request)
            _profileState.value = step2Response
        }
    }




    fun completeProfileCreationFlow(
        auth: String,
        otp: String,
        name: String,
        username: String,
        avatarId: Int
    ): Flow<NetworkResult<ProfileResponse>> = flow {
        // Step 0: Initiate Profile Creation (send OTP)
        emit(NetworkResult.Loading())  // Emit loading state
        val step0Request = NewUserProfileRequest(step = 0, age = "AGE_3_TO_10")
        val step0Response = repository.makeNewProfile(auth, step0Request)
        if (step0Response is NetworkResult.Error) {
            emit(NetworkResult.Error("Step 0 failed: ${step0Response.message}"))
            return@flow  // Stop if step 0 fails.
        }
        // Capture requestId from step 0
        val requestId = (step0Response as NetworkResult.Success).data?.requestId

        // Step 1: Verify OTP using the obtained requestId
        emit(NetworkResult.Loading())  // Indicate next step is starting
        val step1Request = NewUserProfileRequest(
            step = 1,
            requestId = requestId,
            otpCode = otp,
            age = "AGE_3_TO_10"

        )
        val step1Response = repository.makeNewProfile(auth, step1Request)
        if (step1Response is NetworkResult.Error) {
            emit(NetworkResult.Error("Step 1 failed: ${step1Response.message}"))
            return@flow
        }

        // Step 2: Finalize Profile Creation with name & username
        emit(NetworkResult.Loading())  // Indicate next step is starting
        val step2Request = NewUserProfileRequest(
            step = 2,
            requestId = (step1Response as NetworkResult.Success).data?.requestId,
            otpCode = otp,
            name = name,
            username = username,
            avatar = avatarId,
            age = "AGE_3_TO_10"
        )
        val step2Response = repository.makeNewProfile(auth, step2Request)
        // Final emission (either Success or Error) will have the type NetworkResult<ProfileResponse>
        emit(step2Response)
    }.flowOn(Dispatchers.IO)

    fun completeProfileCreation(auth: String) {

        Log.d("profile", "username is:" +_username.value + "name is .." +_name.value)

        viewModelScope.launch {
            completeProfileCreationFlow(auth, otpCode.value, _name.value
                , _username.value,_avatarId.value)
                .collect { result ->
                    _profileState.value = result
                }
        }
    }






    fun getUserProfile()
    {

        _isLoading.value=true

        viewModelScope.launch {

          val result=  repository.getCurrentUserProfile()

            _activeUserProfile.value=result






        }
        _isLoading.value=false












    }
















}





















private fun checkUserLogin()
{









}



















































