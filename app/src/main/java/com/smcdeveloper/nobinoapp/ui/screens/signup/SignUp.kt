package com.smcdeveloper.nobinoapp.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.smcdeveloper.nobinoapp.data.remote.NetworkResult
import com.smcdeveloper.nobinoapp.navigation.Screen
import com.smcdeveloper.nobinoapp.viewmodel.ProfileViewModel

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest

@Composable
fun RegisterScreen(
    navController: NavHostController,
    profileViewModel: ProfileViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    // Observing loginResponse from ProfileViewModel
    LaunchedEffect(Dispatchers.Main) {
        profileViewModel.loginResponse.collectLatest { loginResponse ->
            when (loginResponse) {
                is NetworkResult.Success -> {
                    loginResponse.data?.let { user ->
                        val refNumber = user.ref_number // Assuming `refNumber` is part of the response
                        if (refNumber.isNotEmpty()) {
                            // Navigate to OTP validation screen
                            navController.navigate(Screen.OtpValidation.withArgs(refNumber))
                        }
                    }
                    Toast.makeText(context, "RefNumber retrieved successfully!", Toast.LENGTH_SHORT).show()
                }
                is NetworkResult.Error -> {
                    Toast.makeText(context, loginResponse.message ?: "Error occurred", Toast.LENGTH_LONG).show()
                }
                is NetworkResult.Loading -> {
                    // Optionally show a loading spinner
                }
            }
        }
    }

    // Display Login UI
    LoginScreen(profileViewModel, navController)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    profileViewModel: ProfileViewModel,
    navController: NavHostController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = profileViewModel.inputPhoneState,
            onValueChange = { profileViewModel.inputPhoneState = it },
            label = { Text("Enter Phone Number", color = Color.Gray) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Phone),
            colors = TextFieldDefaults.textFieldColors(
                focusedTextColor = Color.White,
                containerColor = Color.Gray,
                focusedIndicatorColor = Color.Red,
                unfocusedIndicatorColor = Color.DarkGray
            )
        )
        Button(
            onClick = {
                profileViewModel.getOtp() // Trigger OTP retrieval
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
        ) {
            Text(text = "Retrieve OTP", color = Color.White, fontSize = 16.sp)
        }
    }
}
