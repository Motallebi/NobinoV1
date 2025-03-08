import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import androidx.compose.ui.unit.LayoutDirection
import androidx.core.text.isDigitsOnly
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.smcdeveloper.nobinoapp.data.remote.NetworkResult
import com.smcdeveloper.nobinoapp.navigation.Screen
import com.smcdeveloper.nobinoapp.util.AppConfigManager
import com.smcdeveloper.nobinoapp.util.Constants.NOBINO_LOG_TAG
import com.smcdeveloper.nobinoapp.util.DigitHelper
import com.smcdeveloper.nobinoapp.viewmodel.DataStoreViewModel
import com.smcdeveloper.nobinoapp.viewmodel.ProfileViewModel
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OtpValidationScreen(
    navController: NavController,
    refNumber: String,
    profileViewModel: ProfileViewModel = hiltViewModel(),
    dataStoreViewModel: DataStoreViewModel= hiltViewModel()

) {
    val otpLength = 5
    val otpValues = remember { mutableStateListOf("", "", "", "", "") }
    val context = LocalContext.current
    var isValid by remember { mutableStateOf(true) }
    var isEnabled by remember { mutableStateOf(false) }





    // Focus requesters for managing focus between fields
    val focusRequesters = List(otpLength) { FocusRequester() }

    // Initialize refNumber in ViewModel
    LaunchedEffect(refNumber) {
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
                    Toast.makeText(
                        context,
                        loginResponse.message ?: "Invalid OTP or refNumber",
                        Toast.LENGTH_LONG
                    ).show()
                   // otpValues.clear()


                }
                is NetworkResult.Loading -> {
                    // Show a loading spinner (optional)
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
            // Logo
            Text(
                text = "نوینو",
                color = Color.Red,
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 32.dp)
            )

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
            ) {







                repeat(otpLength) { i ->
                    OutlinedTextField(
                        value = otpValues[i],
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
                            Log.d("otp",otp)

                            if(otp.isDigitsOnly() && otp.length==5 )
                            {
                                isEnabled =true

                            }







                        },



                        singleLine = true,
                        modifier = Modifier
                            .size(70.dp)
                            .padding(8.dp)
                            .border(
                                width = 2.dp,
                                color = when {
                                    !isValid -> Color.Red
                                    otpValues[i].isNotEmpty() -> Color.White
                                    else -> Color.Gray
                                },
                                shape = RoundedCornerShape(8.dp)
                            )
                            .background(Color.Black, shape = RoundedCornerShape(8.dp))
                            .focusRequester(focusRequesters[i])
                            .onFocusChanged { focusState ->
                                if (!focusState.isFocused && otpValues[i].isEmpty()) {
                                    isValid = true // Reset validation state for empty fields
                                }
                            },
                        textStyle = TextStyle(
                            color = Color.White,
                            fontSize = 20.sp,
                            textAlign = TextAlign.Center
                        ),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        visualTransformation = VisualTransformation.None,
                        keyboardActions = KeyboardActions {
                            if (i == otpLength - 1) {
                                val otp = otpValues.joinToString("")





                               profileViewModel.inputOtpState = DigitHelper.digitByLocateFaToEn(otp)
                              //  profileViewModel.inputOtpState = otp

                                profileViewModel.validateOtp(
                                   refNumber =  dataStoreViewModel.getUserRefKey().toString(),
                                    otp=profileViewModel.inputOtpState,
                                    mobile = dataStoreViewModel.getUserPhoneNumber().toString()







                                )
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

                        profileViewModel.validateOtp(
                            refNumber = profileViewModel.inputRefSates,
                            otp = profileViewModel.inputOtpState,
                            mobile = dataStoreViewModel.getUserPhoneNumber().toString()


                        )
                    }
                },


                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
            ) {
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


