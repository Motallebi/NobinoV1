package com.smcdeveloper.nobinoapp.ui.screens.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.smcdeveloper.nobinoapp.ui.theme.extraBoldNumber
import com.smcdeveloper.nobinoapp.ui.theme.nobinoLarge

@Composable
fun Profile(navController: NavHostController)
{

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
