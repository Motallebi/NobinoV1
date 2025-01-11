import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.smcdeveloper.nobinoapp.data.remote.NetworkResult
import com.smcdeveloper.nobinoapp.viewmodel.ProfileViewModel

import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@Composable
fun OtpValidationScreen(
    navController: NavController,
    refNumber: String,
    profileViewModel: ProfileViewModel = hiltViewModel(),
    dataStoreViewModel: DataStoreViewModel= hiltViewModel()

) {
    var enteredOtp by remember { mutableStateOf("") }
    val context = LocalContext.current

    // Set the refNumber in the ViewModel
    LaunchedEffect(refNumber) {
        profileViewModel.inputRefSates = refNumber
    }

    // Observe loginResponse for validation results
    LaunchedEffect(Unit) {
        profileViewModel.loginResponse.collectLatest { loginResponse ->
            when (loginResponse) {
                is NetworkResult.Success -> {
                    loginResponse.data?.let { user ->
                        val token =  user.access_token// Assuming token is part of the response
                        if (token.isNotEmpty()) {
                            dataStoreViewModel.saveUserToken(token) // Save token to DataStore
                            navController.navigate("profile_page_route") // Navigate to Profile screen
                        }
                    }
                }
                is NetworkResult.Error -> {
                    Toast.makeText(
                        context,
                        loginResponse.message ?: "Invalid OTP or refNumber",
                        Toast.LENGTH_LONG
                    ).show()
                }
                is NetworkResult.Loading -> {
                    // Show a loading spinner or disable the button
                }
            }
        }
    }

    // OTP Input UI
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = enteredOtp,
            onValueChange = { enteredOtp = it },
            label = { Text("Enter OTP") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                // Set the OTP in the ViewModel
                profileViewModel.inputOtpState = enteredOtp

                // Call the validation function
                profileViewModel.validateOtp()
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
        ) {
            Text("Validate OTP", color = Color.White)
        }
    }
}
