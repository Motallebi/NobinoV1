package com.smcdeveloper.nobinoapp.ui.screens.search

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.smcdeveloper.nobinoapp.ui.theme.extraBoldNumber

@Composable
fun Search(navController: NavHostController)
{
    Box(
        contentAlignment = Alignment.CenterStart


    )
    {

        Text("SERACH"
        ,
            style = MaterialTheme.typography.extraBoldNumber,
            color = Color.Red



        )






    }







}