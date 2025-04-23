package com.smcdeveloper.nobinoapp.ui.screens.splash

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.smcdeveloper.nobinoapp.R
import com.smcdeveloper.nobinoapp.data.remote.CheckConnection.isNetworkAvailable
import com.smcdeveloper.nobinoapp.navigation.Screen
import com.smcdeveloper.nobinoapp.ui.component.Loading3Dots
import kotlinx.coroutines.delay


@Composable
fun SplashScreen(navController: NavHostController)
{


    val context = LocalContext.current
    val isNetworkAvailable = remember {

        isNetworkAvailable(context)
    }

   /* Splash(isNetworkAvailable) {
        if (isNetworkAvailable(context)) {
            navController.navigate(Screen.Home.route) {
                popUpTo(Screen.Splash.route) {
                    inclusive = true
                }
            }
        } else {
            Toast.makeText(
                context,
                "Check net",
                Toast.LENGTH_LONG
            ).show()
        }
    }*/


    LaunchedEffect(true) {
        delay(2500)
        if (isNetworkAvailable) {


                navController.navigate(Screen.Home.route) {
                    popUpTo(Screen.Splash.route) {
                        inclusive = true
                    }
                }
            }


        }





































    Text("This IS Splash Screen")

    Box(

        modifier =
        Modifier.fillMaxSize()
            .background(Color.Black),
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





@Composable
fun Splash(isNetworkAvailable: Boolean, onRetryClick: () -> Unit) {


    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.primary


    )



    {


        Box(
            modifier = Modifier
                //.background(Color.Black)
                .fillMaxSize(0.5f),
            contentAlignment = Alignment.Center
        )

        {
            Image(
                modifier =Modifier,
                painter = painterResource(id =R.drawable.nobino_logo),
                contentDescription = null
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(100.dp),
                contentAlignment = Alignment.BottomCenter
            ) {
                Image(
                    modifier = Modifier.height(30.dp),
                    painter = painterResource(id =R.drawable.sub),
                    contentDescription = null
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                contentAlignment = Alignment.BottomCenter
            ) {
                if (isNetworkAvailable) {
                    Loading3Dots(false)
                } else {
                    ReTry(onRetryClick = onRetryClick)
                }
            }
        }



















    }



}

@Composable
private fun ReTry(onRetryClick: () -> Unit) {
    Column(
        modifier = Modifier
            .clickable(onClick = onRetryClick)
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        androidx.compose.material.Text(
            style = androidx.compose.material.MaterialTheme.typography.h6,
            text = "Check net",
            color = Color.White
        )
        Icon(
            Icons.Filled.Refresh,
            tint = Color.White,
            contentDescription = "",
            modifier = Modifier
                .padding(top = 8.dp)
        )
    }
}






