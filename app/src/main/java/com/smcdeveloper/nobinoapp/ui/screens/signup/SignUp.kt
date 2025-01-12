import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.smcdeveloper.nobinoapp.viewmodel.ProfileViewModel

import kotlinx.coroutines.flow.collectLatest
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import com.smcdeveloper.nobinoapp.R
import com.smcdeveloper.nobinoapp.data.remote.NetworkResult
import com.smcdeveloper.nobinoapp.navigation.Screen
import com.smcdeveloper.nobinoapp.util.Constants.NOBINO_LOG_TAG
import kotlinx.coroutines.Dispatchers



@Composable
fun RegisterScreen(
    navController: NavHostController,
    profileViewModel: ProfileViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    // Observe loginResponse from ProfileViewModel
    LaunchedEffect(Unit) {
        profileViewModel.loginResponse.collect { loginResponse ->
            when (loginResponse) {
                is NetworkResult.Success -> {

                    Log.d(NOBINO_LOG_TAG,"refnun is"+loginResponse.data?.refNumber.toString())
                    Log.d(NOBINO_LOG_TAG,"data is"+loginResponse.data?. toString())
                    loginResponse.data?.let { user ->
                       val refNumber = user.refNumber // Assuming `refNumber` is part of the response
                        if (refNumber.isNotEmpty()) {
                            // Navigate to OTP validation screen
                            navController.navigate(Screen.OtpValidation.withArgs(refNumber))
                        }
                    }
                    Toast.makeText(context, "RefNumber retrieved successfully!", Toast.LENGTH_SHORT).show()
                    profileViewModel.loadingState = false
                }
                is NetworkResult.Error -> {
                    Toast.makeText(context, loginResponse.message ?: "Error occurred", Toast.LENGTH_LONG).show()
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
    navController: NavHostController
) {
    // Main container
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Top Bar
        TopBar()

        // Logo and title
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(vertical = 16.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.bottom_nav_user_selected), // Replace with your logo resource
                contentDescription = null,
                tint = Color.Red,
                modifier = Modifier.size(64.dp)
            )
            Text(
                text = "نوپینو",
                fontSize = 28.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }

        // Phone input and buttons
        Column(
            modifier = Modifier.padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PhoneInputField(profileViewModel)

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    profileViewModel.getOtp() // Trigger OTP retrieval
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
            ) {
                Text(text = "Retrieve OTP via SMS", color = Color.White, fontSize = 16.sp)
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedButton(
                onClick = {
                    // Optional: Handle voice message code retrieval
                },
                border = BorderStroke(1.dp, Color.Red),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Retrieve OTP via Voice", color = Color.Red)
            }
        }

        // Footer text
        Text(
            text = "کاربر گرامی شما می‌توانید پس از تکمیل ثبت‌نام، از سایر محصولات شرکت ارمغان راه طلایی نیز بدون نیاز به ثبت‌نام مجدد، استفاده نمایید.",
            color = Color.White,
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Composable
fun TopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "9:30", color = Color.White)
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.Email,
                contentDescription = null,
                tint = Color.White
            )
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                imageVector = Icons.Default.Email,
                contentDescription = null,
                tint = Color.White
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhoneInputField(profileViewModel: ProfileViewModel) {
    TextField(
        value = profileViewModel.inputPhoneState,
        onValueChange = { profileViewModel.inputPhoneState = it },
        placeholder = { Text(text = "09123456789", color = Color.Gray) },
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(8.dp))
            .padding(horizontal = 16.dp),
        singleLine = true,
        colors = TextFieldDefaults.textFieldColors(
             focusedTextColor =  Color.Black,
            containerColor = Color.White,
            focusedIndicatorColor = Color.Red,
            unfocusedIndicatorColor = Color.Gray
        )
    )
}
