package com.smcdeveloper.nobinoapp.ui.screens.bs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.smcdeveloper.nobinoapp.navigation.BottomNavigationBar
import com.smcdeveloper.nobinoapp.ui.screens.home.NobinoTop

@Composable
fun TestScreen(navController: NavHostController)
{

  ShowContent1()







}

@Composable
fun ShowContent(navController: NavHostController) {

    Scaffold(

        topBar = { NobinoTop(navController) },
        bottomBar = {
            BottomNavigationBar(navController, onItemClick = {
                navController.navigate(it.route)

            }

            )


        },









        ) {

            paddingValues ->

        Box(
            modifier = Modifier.fillMaxSize()
                .padding(paddingValues)
                // .wrapContentSize(unbounded = true)
                // .background(Color.Red)
                .padding(top = 60.dp)
                .height(700.dp)
                .background(MaterialTheme.colorScheme.primary),


            contentAlignment = Alignment.TopStart


        )
        {
            Text("Test Screen")


        }




    }


}



@Composable
fun ShowContent1() {











        Box(
            modifier = Modifier.fillMaxSize()
                //.padding(paddingValues)
                // .wrapContentSize(unbounded = true)
                // .background(Color.Red)
                //.padding(top = 60.dp)
               // .height(700.dp)
                .background(MaterialTheme.colorScheme.primary),


            contentAlignment = Alignment.TopStart


        )
        {
            Text("Test Screen")


        }




    }
















