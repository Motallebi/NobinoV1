import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.smcdeveloper.nobinoapp.R
import com.smcdeveloper.nobinoapp.data.remote.NetworkResult
import com.smcdeveloper.nobinoapp.navigation.Screen
import com.smcdeveloper.nobinoapp.ui.theme.disabledButtonColor
import com.smcdeveloper.nobinoapp.ui.theme.nobinoLarge
import com.smcdeveloper.nobinoapp.ui.theme.nobinoMedium
import com.smcdeveloper.nobinoapp.ui.theme.roundedShape
import com.smcdeveloper.nobinoapp.util.Constants.NOBINO_LOG_TAG
import com.smcdeveloper.nobinoapp.util.DigitHelper
import com.smcdeveloper.nobinoapp.viewmodel.DataStoreViewModel
import com.smcdeveloper.nobinoapp.viewmodel.ProfileViewModel


@Composable
fun RegisterScreen(
    navController: NavHostController,
    profileViewModel: ProfileViewModel = hiltViewModel(),
    dataStoreViewModel: DataStoreViewModel = hiltViewModel()


) {
    val context = LocalContext.current

    // Observe loginResponse from ProfileViewModel
    LaunchedEffect(Unit) {
        profileViewModel.loginResponse.collect { loginResponse ->
            when (loginResponse) {
                is NetworkResult.Success -> {

                    Log.d(NOBINO_LOG_TAG, "refnun is" + loginResponse.data?.refNumber.toString())
                    Log.d(NOBINO_LOG_TAG, "data is" + loginResponse.data?.toString())
                    loginResponse.data?.let { user ->
                        val refNumber =
                            user.refNumber // Assuming `refNumber` is part of the response
                        if (refNumber.isNotEmpty()) {
                            // Navigate to OTP validation screen
                            navController.navigate(Screen.OtpValidation.withArgs(refNumber))
                        }
                    }
                    Toast.makeText(context, "RefNumber retrieved successfully!", Toast.LENGTH_SHORT)
                        .show()
                    profileViewModel.loadingState = false
                }

                is NetworkResult.Error -> {
                    Toast.makeText(
                        context, loginResponse.message ?: "Error occurred", Toast.LENGTH_LONG
                    ).show()
                    profileViewModel.loadingState = false
                }

                is NetworkResult.Loading -> {
                    // Optionally handle loading state
                }
            }
        }
    }

    // Display Login UI
    LoginScreen(profileViewModel, navController)
}

@Composable
fun LoginScreen(
    profileViewModel: ProfileViewModel,
    navController: NavHostController,
    dataStoreViewModel: DataStoreViewModel = hiltViewModel()
) {
    // Main container


    Column(
        modifier = Modifier.fillMaxSize(),
        //  .background(Color.Green),
        horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center
    )

    {


        // Top Bar


        // Logo and title


        Column(
            modifier = Modifier
                .fillMaxWidth()

                //  .background(Color.Blue)
                .padding(horizontal = 16.dp)
                .padding(vertical = 16.dp),


            horizontalAlignment = Alignment.Start


        )

        {

            Row(
                modifier = Modifier.padding(bottom = 8.dp, start = 16.dp)


            )

            {

                Text(
                    "ورود", style = MaterialTheme.typography.nobinoLarge


                )


            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, top = 10.dp, bottom = 10.dp)

            ) {

                Text(
                    DigitHelper.digitByLocate(stringResource(R.string.input_phone_please)),
                    style = MaterialTheme.typography.nobinoMedium


                )


            }


            ShowInputForms(profileViewModel, dataStoreViewModel)


        }


        // Phone input and buttons


        Text(
            text = "کاربر گرامی شما می‌توانید پس از تکمیل ثبت‌نام، از سایر محصولات شرکت ارمغان راه طلایی نیز بدون نیاز به ثبت‌نام مجدد، استفاده نمایید.",
            color = Color.White,
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(16.dp)
        )


    }


    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
        // .background(Color.Green)

        ,


        contentAlignment = Alignment.Center


    ) {

        Image(
            painter = painterResource(id = R.drawable.nobino_logo_title_large), // Replace with your logo resource
            contentDescription = null,
            // tint = Color.Red,
            // modifier = Modifier.size(64.dp)
        )


    }


    // Footer text

}


@Composable
fun ShowInputForms(profileViewModel: ProfileViewModel, dataStoreViewModel: DataStoreViewModel) {


    var isPhoneValid by remember { mutableStateOf(false) }


    PhoneInputField(profileViewModel, dataStoreViewModel = dataStoreViewModel,

        onPhoneDataChanged = {

                isPhoneValid=it

        }


    )


    Spacer(modifier = Modifier.height(16.dp))

    Button(


        onClick = {

            if (isPhoneValid) {

                profileViewModel.getOtp() // Trigger OTP retrieval
            }


        },

        shape = MaterialTheme.roundedShape.medium,

        enabled = isPhoneValid,

        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp),

        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Red,
            disabledContentColor = Color.Blue,
            disabledContainerColor = MaterialTheme.colorScheme.disabledButtonColor.copy(alpha = 0.8f)


        )
    ) {
        Text(text = "دریافت کد از طریق پیامک", color = Color.White, fontSize = 16.sp)
    }

    Spacer(modifier = Modifier.height(16.dp))

    OutlinedButton(
        onClick = {
            // Optional: Handle voice message code retrieval
        },
        border = BorderStroke(1.dp, Color.Red),
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp),
        shape = MaterialTheme.roundedShape.medium


    ) {
        Text(text = "دریافت کد از طریق پیام صوتی", color = Color.Red)
    }


}


fun validate(phoneNumber: String): Boolean = phoneNumber.length == 11


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhoneInputField(
    profileViewModel: ProfileViewModel,
    dataStoreViewModel: DataStoreViewModel,
    onPhoneDataChanged: (Boolean) -> Unit

) {
    CompositionLocalProvider(LocalLayoutDirection provides androidx.compose.ui.unit.LayoutDirection.Ltr) {


        OutlinedTextField(

            value = DigitHelper.digitByLocate(profileViewModel.inputPhoneState),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),


            onValueChange = { newValue ->

                if (newValue.all { it.isDigit() } && newValue.length <= 11


                ) { // ✅ Only digits, max 11


                    profileViewModel.inputPhoneState = newValue
                    dataStoreViewModel.saveUserPhone(newValue)
                    onPhoneDataChanged(
                        newValue.length == 11 && newValue.startsWith(
                            DigitHelper.digitByLocate(
                                "09"
                            )
                        )
                    )

                }


            },
            leadingIcon = { Icon(painterResource(R.drawable.phone_regular), "") },
            trailingIcon = {
                Icon(
                    painterResource(R.drawable.eye), "", modifier = Modifier.size(32.dp)


                )
            },


            placeholder = {

                Text(
                    text = DigitHelper.digitByLocate("09123456789"),
                    color = Color.Gray,
                    textAlign = TextAlign.Left


                )
            },
            modifier = Modifier
                .fillMaxWidth()


                .background(Color.Black, RoundedCornerShape(8.dp)),
            // .padding(horizontal = 16.dp),
            singleLine = true,
            colors = TextFieldDefaults.textFieldColors(
                focusedTextColor = Color.White,
                containerColor = Color.Black,
                focusedIndicatorColor = Color.Red,
                unfocusedIndicatorColor = Color.Gray
            ),

            shape = MaterialTheme.roundedShape.medium


        )


    }


}
