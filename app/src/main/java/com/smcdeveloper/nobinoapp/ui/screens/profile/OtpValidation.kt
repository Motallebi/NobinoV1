import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.core.text.isDigitsOnly
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.smcdeveloper.nobinoapp.R
import com.smcdeveloper.nobinoapp.data.remote.NetworkResult
import com.smcdeveloper.nobinoapp.navigation.Screen
import com.smcdeveloper.nobinoapp.ui.screens.profile.ValidationStatus
import com.smcdeveloper.nobinoapp.util.AppConfigManager
import com.smcdeveloper.nobinoapp.util.Constants.NOBINO_LOG_TAG
import com.smcdeveloper.nobinoapp.util.Constants.USER_TOKEN
import com.smcdeveloper.nobinoapp.util.DigitHelper
import com.smcdeveloper.nobinoapp.viewmodel.DataStoreViewModel
import com.smcdeveloper.nobinoapp.viewmodel.ProfileViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OtpValidationScreen(
    navController: NavController,
    refNumber: String,
    name:String,
    username:String,
    avatarId:Int=1,

    profileViewModel: ProfileViewModel = hiltViewModel(),
    dataStoreViewModel: DataStoreViewModel= hiltViewModel()


) {






    val otpLength = 5
    var otpValues = mutableStateListOf("", "", "", "", "" )
    val context = LocalContext.current
    var isValid by remember { mutableStateOf(true) }
    var isEnabled by remember { mutableStateOf(false) }
    val profileState by profileViewModel.profileState.collectAsState()
    var validationStatus = profileViewModel.status
    var errorTrigger by remember { mutableStateOf(0) }




    // Focus requesters for managing focus between fields
    val focusRequesters = List(otpLength) { FocusRequester() }

   /* LaunchedEffect(validationStatus) {

        Log.d("valid",validationStatus.toString())
        if (validationStatus == ValidationStatus.Error || validationStatus == ValidationStatus.Success) {
            delay(2000L) // 2 seconds
            validationStatus = ValidationStatus.Default
            otpValues= mutableStateListOf("", "", "", "", "")
        }
    }*/
    var status by remember { mutableStateOf(ValidationStatus.Default) }

   /* LaunchedEffect(Unit) {
        profileViewModel.validationEvents.collect { newStatus ->

            if (newStatus is ValidationStatus.Error) {
                delay(5000)

                status = ValidationStatus.Default
                otpValues= mutableStateListOf("", "", "", "", "")
            }
        }
    }*/







    // Initialize refNumber in ViewModel
    LaunchedEffect(refNumber) {
        if(refNumber=="SubProfile")
        {
            profileViewModel.initiateProfileCreation(auth = "Bearer $USER_TOKEN", name = name, username = username, avatarId = avatarId)


        }
        profileViewModel.inputRefSates = refNumber
        dataStoreViewModel.saveUserRefKey(refNumber)

    }

    // Observe loginResponse
    LaunchedEffect(Unit) {
        profileViewModel.loginResponse.collectLatest { loginResponse ->
            when (loginResponse) {
                is NetworkResult.Success -> {
                    loginResponse.data?.let { user ->
                        val token = user.access_token
                        Log.d(NOBINO_LOG_TAG,"Token is $token" )




                        if (token.isNotBlank()) {
                            dataStoreViewModel.saveUserToken(token)
                            navController.navigate(Screen.Profile.route)
                            AppConfigManager.updateToken(token)
                        }
                    }
                }
                is NetworkResult.Error -> {
                    profileViewModel.triggerError()
                    Toast.makeText(
                        context,
                        loginResponse.message+"Error...." ?: "Invalid OTP or refNumber",
                        Toast.LENGTH_LONG
                    ).show()
                   // otpValues.clear()
                   // otpValues= mutableStateListOf("", "", "", "", "")

                }
                is NetworkResult.Loading -> {
                    // Show a loading spinner (optional)
                }
            }
        }
    }


    LaunchedEffect(Unit) {


      profileViewModel.profileState.collectLatest { response->

          when(response)
          {
              is NetworkResult.Success->
              {


              }

              is NetworkResult.Error -> {
                  Log.d("valid1","Status is Network Error")
                  isValid=false
                //  profileViewModel.status=ValidationStatus.Error
                //  errorTrigger++
                  profileViewModel.triggerError()
               //   profileViewModel.setError()



                  Toast.makeText(
                      context,
                      response.message ?: "Invalid OTP or refNumber",
                      Toast.LENGTH_LONG
                  ).show()

              }
              is NetworkResult.Loading -> {



              }
          }







      }




    }











    // Functions for onResendOtp and onEditPhoneNumber
    val onResendOtp = {
        profileViewModel.requestNewOtp()
        Toast.makeText(context, "کد تایید جدید ارسال شد", Toast.LENGTH_SHORT).show()
    }




    val onEditPhoneNumber = {
        navController.popBackStack()
    }

    // Force layout to LTR
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row(modifier = Modifier,
                horizontalArrangement = Arrangement.spacedBy(16.dp)


            )
            {

                Text(
                    text = "نوبینو",
                    color = Color.White,
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 32.dp)
                )
                Image(painterResource(R.drawable.nobino_logo),"")






            }

            // Logo


            // Title
            Text(
                text = "کد تایید",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Instructions
            Text(
                text = "لطفاً کد ارسال شده به شماره  را وارد نمایید",
                color = Color.Gray,
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            Text(
                text = DigitHelper.digitByLocate("09128248661"),
                color = Color.Gray,
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 32.dp)
            )




            // OTP Input Fields
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                    //.padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            )

            {







                repeat(otpLength) { i ->
                    NobinoValidationText(
                        otpValues,
                        i,
                        otpLength,
                        focusRequesters,
                        isEnabled,
                        profileViewModel,
                        refNumber,
                        navController,
                        dataStoreViewModel,
                        isValid=isValid,
                        validationStatus = status
                    )



                }





            }

            Spacer(modifier = Modifier.height(16.dp))

            // Validate OTP Button
            val otp = otpValues.joinToString("")
            Log.d("otp","is enabled button  $isEnabled")

            Button(

                enabled = isEnabled,

                onClick = {

                    if(isEnabled ) {
                        Log.d("otp", "otp is ${DigitHelper.digitByLocateFaToEn(otp)}")


                        profileViewModel.inputOtpState = DigitHelper.digitByLocateFaToEn(otp)



                        if(refNumber != "SubProfile") {
                            profileViewModel.validateOtp(
                                refNumber = profileViewModel.inputRefSates,
                                otp = profileViewModel.inputOtpState,
                                mobile = dataStoreViewModel.getUserPhoneNumber().toString(),
                                onValidate = {}


                            )
                        }
                        else
                        {
                             // Update the OTP in the ViewModel
                            profileViewModel.setOtpCode(DigitHelper.digitByLocateFaToEn(otp))
                            // Call completeProfileCreation to trigger OTP verification and final user creation
                            profileViewModel.completeProfileCreation(auth ="Bearer $USER_TOKEN" )
                            navController.popBackStack()

















                        }


                    }
                },


                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red),

                )



































            {
                Text("تایید کد", color = Color.White)
            }



            Spacer(modifier = Modifier.height(16.dp))

            // Resend OTP Button
            Button(
                onClick = onResendOtp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "دریافت کد از طریق پیامک", color = Color.White)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Edit Phone Number Option
            TextButton(
                onClick = {

                    otpValues.forEachIndexed { index,_  ->
                        otpValues[index]=""

                    }









                },
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text(text = "ویرایش شماره همراه", color = Color.Red,
                    modifier = Modifier.clickable {


                        navController.navigate(Screen.SignUp.route)




                    }





                )




            }
        }
    }
}

@SuppressLint("UnrememberedMutableState")
@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun NobinoValidationText(
    otpValues: MutableList<String>,
    i: Int,
    otpLength: Int,
    focusRequesters: List<FocusRequester>,
    isEnabled: Boolean,
    profileViewModel: ProfileViewModel,
    refNumber: String,
    navController: NavController,
    dataStoreViewModel: DataStoreViewModel,
    isValid: Boolean,
    validationStatus: ValidationStatus

) {

    Log.d("valid","ivalue: $i")

   val st= getValidationStatus(profileViewModel)
    var color=Color.White
    val err = mutableStateOf("")
    when(st.value)
    {
       ValidationStatus.Default -> { Log.d("valid","Default")
           color= Color.Gray
           err.value=otpValues[i]
        }
        ValidationStatus.Error -> {
            Log.d("valid","Error")
            color=Color.Red
            err.value=""
            otpValues.forEachIndexed(){index,_->
                otpValues[index]=""

            }


        }
        ValidationStatus.Success -> {
            Log.d("valid","Success")
            color=Color.Green
            err.value=""
        }
    }




    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var color2 =Color.White
    LaunchedEffect(Unit) {
        Log.d("valid","Launch..."+validationStatus.toString())

             profileViewModel.validationEvents.collectLatest{

                 color2= when(it)
                 {
                     ValidationStatus.Default ->Color.Gray
                     ValidationStatus.Error -> Color.Red
                     ValidationStatus.Success -> Color.White
                 }



            }
        }


    val status by profileViewModel.validationStatus.collectAsState()


    val color1= when(status)
    {
          ValidationStatus.Default ->Color.Gray
         ValidationStatus.Error -> Color.Red
        ValidationStatus.Success -> Color.White
    }
    val errorVlue = when(validationStatus)
    {
        ValidationStatus.Default->otpValues[i]
        ValidationStatus.Error -> ""
        ValidationStatus.Success ->otpValues[i]
    }





    var isEnabled1 = isEnabled
    OutlinedTextField(
        value = err.value,
        onValueChange = { value ->
            if (value.length <= 1) {
                otpValues[i] = DigitHelper.digitByLocate(value)


                //  otpValues[i] = value


                if (value.isNotBlank() && i < otpLength - 1) {
                    // Move focus to the next field
                    focusRequesters[i + 1].requestFocus()
                } else if (value.isEmpty() && i > 0) {
                    // Move focus to the previous field
                    focusRequesters[i - 1].requestFocus()
                }
            }


            val otp = otpValues.joinToString("")
            Log.d("otp", otp)

            if (otp.isDigitsOnly() && otp.length == 5) {
                isEnabled1 = true
                //  isValid=true
                validateOtpCode(
                    profileViewModel = profileViewModel,
                    refNumber = refNumber,
                    otp = otp,
                    navController = navController,
                    dataStoreViewModel = dataStoreViewModel,
                    onValidate = {validationStatus}


                )


            }


        },




        singleLine = true,
        modifier = Modifier
            .size(70.dp)
            .padding(8.dp)
            .border(
                width = 2.dp,
                color =color,
                /* color = if(otpValues[i].isNotEmpty() && )

                color = when() {

                    validationStatus -> Color.Green

                    otpValues[i].isNotEmpty() -> Color.White
                    else -> Color.Gray
                },*/
                shape = RoundedCornerShape(8.dp)
            )
            .background(Color.Black, shape = RoundedCornerShape(8.dp))
            .focusRequester(focusRequesters[i])
            .onFocusChanged { focusState ->
                if (!focusState.isFocused && otpValues[i].isEmpty()) {

                    Log.d("color", "isvalid $isValid")


                    //  isValid = true // Reset validation state for empty fields
                }
            },
        textStyle = TextStyle(
            color = if (isValid) Color.White else Color.Red,
            fontSize = 20.sp,
            textAlign = TextAlign.Center
        ),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        visualTransformation = VisualTransformation.None,
        keyboardActions = KeyboardActions {
            if (i == otpLength - 1) {
                val otp = otpValues.joinToString("")





                profileViewModel.inputOtpState = DigitHelper.digitByLocateFaToEn(otp)
                if (refNumber == "SubProfile") {
                    profileViewModel.setOtpCode(DigitHelper.digitByLocateFaToEn(otp))
                }


                //  profileViewModel.inputOtpState = otp
                else {
                    profileViewModel.validateOtp(
                        refNumber = dataStoreViewModel.getUserRefKey().toString(),
                        otp = profileViewModel.inputOtpState,
                        mobile = dataStoreViewModel.getUserPhoneNumber().toString(),
                        onValidate = {profileViewModel.status}



                    )

                }


            }
        },
        colors = TextFieldDefaults.textFieldColors(
            focusedTextColor = Color.White,
            cursorColor = Color.Green,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
}


fun validateOtpCode(profileViewModel: ProfileViewModel
                    ,refNumber: String,
                    otp:String,
                    dataStoreViewModel: DataStoreViewModel,
                    navController: NavController,
                    onValidate:()->ValidationStatus

                    )
{


    profileViewModel.inputOtpState = DigitHelper.digitByLocateFaToEn(otp)




    if(refNumber != "SubProfile") {
        profileViewModel.validateOtp(
            refNumber = profileViewModel.inputRefSates,
            otp = profileViewModel.inputOtpState,
            mobile = dataStoreViewModel.getUserPhoneNumber().toString(),
            onValidate = {onValidate()}

        )

    }
    else
    {
        // Update the OTP in the ViewModel
        profileViewModel.setOtpCode(DigitHelper.digitByLocateFaToEn(otp))
        // Call completeProfileCreation to trigger OTP verification and final user creation
        profileViewModel.completeProfileCreation(auth ="Bearer $USER_TOKEN" )
        navController.popBackStack()

















    }


















}

@Composable
fun NovinoValidationOutLineText()
{






}
@Composable
fun getValidationStatus(profileViewModel: ProfileViewModel)=


    profileViewModel.validationStatus.collectAsState()






