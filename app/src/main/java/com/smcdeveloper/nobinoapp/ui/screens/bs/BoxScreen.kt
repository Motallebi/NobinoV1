package com.smcdeveloper.nobinoapp.ui.screens.bs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.smcdeveloper.nobinoapp.ui.component.NobinoButton
import com.smcdeveloper.nobinoapp.ui.component.NobinoOutlineButton
import com.smcdeveloper.nobinoapp.ui.component.NobinoText
import com.smcdeveloper.nobinoapp.ui.theme.mianAppBackGround

@Composable
fun BoxScreen(navController : NavHostController)
{

    Box(
        modifier = Modifier.fillMaxSize()
            .background(color = MaterialTheme.colorScheme.mianAppBackGround)
            .padding(top = 100.dp, start = 30.dp, end = 30.dp),
        contentAlignment = Alignment.Center





    )
    {

        Box(
            modifier = Modifier.fillMaxSize()
                .background(Color.Red),
            contentAlignment = Alignment.TopCenter








        )
        {
            Column() {

                NobinoText()
                NobinoButton("")
                NobinoOutlineButton()



            }







        }




    }







}


