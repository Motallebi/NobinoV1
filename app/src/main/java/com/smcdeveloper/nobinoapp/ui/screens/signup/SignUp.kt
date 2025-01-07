package com.smcdeveloper.nobinoapp.ui.screens.signup

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.smcdeveloper.nobinoapp.R
import com.smcdeveloper.nobinoapp.ui.component.NobinoDefultButton
import com.smcdeveloper.nobinoapp.ui.screens.profile.ProfileScreenState
import com.smcdeveloper.nobinoapp.viewmodel.ProfileViewModel


@Composable
fun RegisterScreen(
    navController: NavHostController,
    profileViewModel: ProfileViewModel= hiltViewModel()





)
{

    LoginScreen(profileViewModel)

   // Text("SignUp")

}


@Composable
fun LoginScreen(profileViewModel: ProfileViewModel) {
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
        )
        {
            PhoneInputField()
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
// Handle SMS code retrieval
 }
                ,
                colors = ButtonDefaults.buttonColors(

                    ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "دریافت کد از طریق پیامک", color = Color.White)
            }
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedButton(
                onClick = {
// Handle voice message code retrieval
 },
                border = BorderStroke(1.dp, Color.Red),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "دریافت کد از طریق پیام صوتی", color = Color.Red)
            }

            NobinoDefultButton(
                "test---",
                {

                    profileViewModel.screenState= ProfileScreenState.PROFILE_STATE



                }


            )




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
                imageVector =Icons.Default.Email,
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

@Composable
fun PhoneInputField() {
    var phoneNumber by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }

    TextField(
        value = phoneNumber,
        onValueChange = { phoneNumber = it },
        placeholder = { Text(text = "09123456789", color = Color.Gray) },
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(8.dp))
            .padding(horizontal = 16.dp),
        singleLine = true,
        trailingIcon = {
            IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                Icon(
                    imageVector = if (isPasswordVisible) Icons.Rounded.Visibility else Icons.Rounded.VisibilityOff,
                    contentDescription = if (isPasswordVisible) "Hide phone number" else "Show phone number"
                )

            }
        },
        colors = TextFieldDefaults.colors(
            focusedTextColor = Color.Green



        )
    )
}
