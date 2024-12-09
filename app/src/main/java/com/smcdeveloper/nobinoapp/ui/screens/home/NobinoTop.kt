package com.smcdeveloper.nobinoapp.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
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

import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavHostController
import com.smcdeveloper.nobinoapp.navigation.Screen


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NobinoTop(navController: NavHostController) {
    var selectedTab by remember { mutableStateOf("نوینو") }




    TopAppBar(
        title = {},
        actions = {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            )

            {
                val tabs = listOf("نوینو", "فیلم", "سریال", "آموزش", "کودک")
                tabs.forEach { tab ->
                    AppBarItem(
                        label = tab,
                        isSelected = tab == selectedTab,
                        onClick = {
                            selectedTab = tab
                            navController.navigate(
                                when (tab) {
                                    "نوینو" ->Screen.Home.route
                                    "فیلم" -> Screen.Home.route
                                    "سریال" -> Screen.Home.route
                                    "آموزش" -> Screen.Home.route
                                    "کودک" -> Screen.Home.route
                                    else -> Screen.Home.route
                                }
                            )
                        }
                    )
                }
            }


        },

        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background
        ),


        )


}


@Composable
fun AppBarItem(label: String, isSelected: Boolean, onClick: () -> Unit) {
    TextButton(onClick = onClick)
    {
       Text(
           text = label,
           color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground,
           fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal




       )
    }
}



































