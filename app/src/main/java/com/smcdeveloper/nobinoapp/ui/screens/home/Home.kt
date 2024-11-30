package com.smcdeveloper.nobinoapp.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.smcdeveloper.nobinoapp.ui.component.NobinoButton
import com.smcdeveloper.nobinoapp.ui.component.NobinoOutlineButton

@Composable
fun Home(navController: NavHostController)
{
 Column(
    modifier = Modifier.padding(top = 30.dp)


 )
 {

    NobinoButton("دریافت کد از طریق پیامک")
    // Text("hi.......")
    NobinoOutlineButton()




 }




}