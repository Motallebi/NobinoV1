package com.smcdeveloper.nobinoapp.ui.screens.categories

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.smcdeveloper.nobinoapp.ui.theme.extraBoldNumber

@Composable
fun Categories(navController: NavHostController) {

    Box(
        contentAlignment = Alignment.CenterStart


    )
    {

        Text(
            "CATEGORIES",
            style = MaterialTheme.typography.extraBoldNumber,
            color = Color.Red


        )


    }
}