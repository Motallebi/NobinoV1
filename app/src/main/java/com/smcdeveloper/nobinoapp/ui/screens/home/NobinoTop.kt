package com.smcdeveloper.nobinoapp.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.smcdeveloper.nobinoapp.R
import com.smcdeveloper.nobinoapp.navigation.Screen
import com.smcdeveloper.nobinoapp.ui.theme.nobinoLarge
import com.smcdeveloper.nobinoapp.ui.theme.primaryContainerLight
import com.smcdeveloper.nobinoapp.ui.theme.selectedBottomBar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NobinoTop(navController: NavHostController) {
    var selectedTab by remember { mutableStateOf("نوینو") }

    //val colors = MaterialTheme.colorScheme // Get the theme colors


    TopAppBar(


        title = {},


        navigationIcon = { // ✅ Add an icon at the start
            IconButton(onClick = { /* Handle icon click */ }) {
                Image (
                    painterResource(R.drawable.nobino_logo), // Replace with your desired icon
                    contentDescription = "Menu",
                   // Modifier.padding(start = 20.dp, end = 20.dp)
                    //tint = Color.White
                )
            }
        },









        actions = {
            Spacer(modifier = Modifier.width(40.dp)) // ✅ Add spacing to avoid overlap


            Row(
                modifier = Modifier.fillMaxWidth()

                ,

                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically

            )

            {
                val tabs = listOf("نوبینو", "فیلم", "سریال", "آموزش", "کودک")
                tabs.forEachIndexed { index, tab ->
                    val style =MaterialTheme.typography.nobinoLarge
                    AppBarItem(


                        label = tab,
                      //  style = if (index==1) MaterialTheme.typography.nobinoLarge else MaterialTheme.typography.titleSmall,
                        isSelected = tab == selectedTab,
                        style = if (index==0) style else MaterialTheme.typography.titleSmall,

                        //isBold = index == 0, // ✅ First tab is bold
                        //fontSize = if (index == 0) 20.sp else 16.sp, // ✅ First tab is larger

                        onClick = {
                            selectedTab = tab
                            navController.navigate(
                                when (tab) {
                                    "نوینو" ->Screen.Home.route
                                    "فیلم" -> Screen.Movies.route
                                    "سریال" -> Screen.Series.route
                                    "آموزش" -> Screen.Training.route
                                    "کودک" -> Screen.Kids.route
                                    else -> Screen.Home.route
                                }
                            )
                        }
                    )
                }
            }


        },

       /* colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.onBackground,
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
            titleContentColor = MaterialTheme.colorScheme.error




        ),*/


        )


}


@Composable
fun AppBarItem(label: String, isSelected: Boolean, style: TextStyle ,onClick: () -> Unit) {
    TextButton(onClick = onClick)
    {

        //style= if (isSelected) MaterialTheme.typography.titleMedium else MaterialTheme.typography.titleSmall

        Text(
           text = label,
           color = if (isSelected) primaryContainerLight else MaterialTheme.colorScheme.onBackground,
           fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
           //style = if (isSelected) MaterialTheme.typography.nobinoLarge else MaterialTheme.typography.titleSmall
            style = style





       )
    }
}



































