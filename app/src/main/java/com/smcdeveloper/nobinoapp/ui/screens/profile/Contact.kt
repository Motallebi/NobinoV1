package com.smcdeveloper.nobinoapp.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController


@Composable
fun ContactUs(navController: NavHostController)
{

   Contact(navController=navController)






}

@Composable
fun Contact(navController:NavController)
{

    Box(modifier =Modifier.fillMaxSize(0.8f)
        .background(Color.Red)
        .padding(16.dp)
    )
    {

        Text("Contact US")





    }


}