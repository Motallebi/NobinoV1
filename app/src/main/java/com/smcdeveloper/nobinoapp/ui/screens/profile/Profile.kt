package com.smcdeveloper.nobinoapp.ui.screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.smcdeveloper.nobinoapp.R
import com.smcdeveloper.nobinoapp.ui.theme.extraBoldNumber
import com.smcdeveloper.nobinoapp.ui.theme.nobinoLarge
import com.smcdeveloper.nobinoapp.ui.theme.nobinoMedium

@Composable
fun Profile(navController: NavHostController)
{

   ProfilePage()

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
fun ProfilePage() {
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
    ProfilePage()
}






