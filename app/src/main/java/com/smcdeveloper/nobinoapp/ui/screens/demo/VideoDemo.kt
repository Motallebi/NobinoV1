package com.smcdeveloper.nobinoapp.ui.screens.demo

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.smcdeveloper.nobinoapp.ui.theme.nobinoLarge

@Composable
fun VideoDemo(
    navController: NavHostController,
    videUrl:String

)
{

    Log.d("video","videoUrlis....$videUrl")

    Box(modifier = Modifier.fillMaxSize()
        .background(Color.Gray),
        contentAlignment = Alignment.Center

    )
    {
        Text("VideoScreen",
            style = MaterialTheme.typography.nobinoLarge


            )


    }







}