package com.smcdeveloper.nobinoapp.ui.screens.profile

import ProductDetailPage
import SubscriptionSelectionPage
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.smcdeveloper.nobinoapp.R
import com.smcdeveloper.nobinoapp.ui.screens.login.LoginScreen

import com.smcdeveloper.nobinoapp.ui.screens.signup.RegisterScreen
import com.smcdeveloper.nobinoapp.ui.theme.nobinoLarge
import com.smcdeveloper.nobinoapp.ui.theme.nobinoMedium
import com.smcdeveloper.nobinoapp.viewmodel.ProfileViewModel

@Composable
fun ProfileScreen(
    navController: NavHostController,
    profileViewModel: ProfileViewModel= hiltViewModel()




)




{

    when(profileViewModel.screenState)
    {
        ProfileScreenState.LOGIN_STATE->
        {
            RegisterScreen(navController)


        }

        ProfileScreenState.PROFILE_STATE ->{

            //ProfilePage()
           // EditUserInfoPage()
           //PaymentHistoryPage()
           // SubscriptionSelectionPage()
          // SubscriptionConfirmationPage()
            //PaymentSuccessPage()

           // NewMemberPage()

          //  ProfilePictureSelectionPage()
         // ProductDetailPage(navController)






        }
        ProfileScreenState.SET_PASSWORD_STATE ->{}
    }







    Box(
        contentAlignment = Alignment.CenterStart


    )
    {



        Text(
            "Profile",
            style = MaterialTheme.typography.nobinoLarge,
           // color = Color.Yellow


        )


    }



}

@Composable
fun Profile1(){

    @Composable
    fun ProfilePage2() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // Profile Picture
            Image(
                painter = painterResource(id = R.drawable.profile_image),
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .border(2.dp, Color.Gray, CircleShape),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Name
            Text(
                text = "John Doe",
                style = MaterialTheme.typography.nobinoLarge.copy(
                    fontWeight = FontWeight.Bold
                )
            )

            // Bio/Description
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Android Developer | Tech Enthusiast | Open Source Contributor",
                style = MaterialTheme.typography.nobinoLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Buttons
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(onClick = { /* Follow Action */ }) {
                    Text("Follow")
                }
                OutlinedButton(onClick = { /* Message Action */ }) {
                    Text("Message")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Details List
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ProfileDetailItem(icon = Icons.Default.LocationOn, detail = "San Francisco, CA")
                ProfileDetailItem(icon = Icons.Default.Face, detail = "Works at Google")
                ProfileDetailItem(icon = Icons.Default.Email, detail = "johndoe@example.com")
            }
        }
    }
}



@Composable
fun ProfileDetailItem(icon: ImageVector, detail: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Icon(imageVector = icon, contentDescription = null, tint = Color.Gray)
        Text(
            text = detail,
            style = MaterialTheme.typography.nobinoMedium
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ProfilePagePreview() {
   // ProfilePage()
}

@Composable
fun ProfilePage() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1A1818))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        ProfileSection()
        Divider(color = Color.Gray, thickness = 1.dp)
        MenuOptions()
    }
}

@Composable
fun ProfileSection() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Existing Profiles
        Row(verticalAlignment = Alignment.CenterVertically) {
            ProfileAvatar(name = "18+ years")
            Spacer(modifier = Modifier.width(16.dp))
            ProfileAvatar(name = "3-10 years")
        }

        // Add New Profile
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.clickable { /* Handle click */ }
        ) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .background(Color.Red, shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text("+", style = TextStyle(fontSize = 24.sp, color = Color.White))
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text("New Account", fontSize = 12.sp)
        }
    }
}

@Composable
fun ProfileAvatar(name: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(56.dp)
                .background(Color.LightGray, shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            // Placeholder for Avatar Image
            Image(
                painter = painterResource(id = android.R.drawable.ic_menu_gallery),
                contentDescription = null,
                modifier = Modifier.size(40.dp)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(name, fontSize = 12.sp)
    }
}

@Composable
fun MenuOptions() {
    val options = listOf(
        "Edit Profile" to android.R.drawable.ic_menu_edit,
        "Subscription" to android.R.drawable.ic_menu_manage,
        "Payment History" to android.R.drawable.ic_menu_view,
        "FAQs" to android.R.drawable.ic_menu_help,
        "Terms & Conditions" to android.R.drawable.ic_menu_info_details,
        "Contact Us" to android.R.drawable.ic_menu_call,
        "Logout" to android.R.drawable.ic_menu_close_clear_cancel
    )

    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        options.forEach { (label, icon) ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { /* Handle click */ }
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = icon),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = Color.Black
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(label, fontSize = 16.sp, fontWeight = FontWeight.Medium)
            }
        }
    }
}








