package com.smcdeveloper.nobinoapp.ui.screens.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.smcdeveloper.nobinoapp.R
import com.smcdeveloper.nobinoapp.ui.theme.splashBg

@Composable
fun SplashScreen(navController: NavHostController)
{

    Text("This IS Splash Screen")

    Box(

        modifier =
        Modifier.fillMaxSize()
            .background(MaterialTheme.colorScheme.splashBg),
        contentAlignment = Alignment.Center







    )
    {

        Image(
            modifier = Modifier.size(250.dp),
            painter = painterResource(id = R.drawable.nobino_logo),
            contentDescription = null
        )







    }


}

