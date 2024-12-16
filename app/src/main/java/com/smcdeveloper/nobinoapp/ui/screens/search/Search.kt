package com.smcdeveloper.nobinoapp.ui.screens.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.smcdeveloper.nobinoapp.ui.screens.home.getAllMovies
import com.smcdeveloper.nobinoapp.ui.screens.home.getSlider
import com.smcdeveloper.nobinoapp.ui.theme.extraBoldNumber

@Composable
fun Search(navController: NavHostController) {
    Box(

        modifier = Modifier.padding(20.dp)

    )
    {
        Column(
            modifier = Modifier.fillMaxSize()
                .background(Color.Green)


        )
        {
            Text("",
                modifier = Modifier
                    .background(Color.Red )



            )
            getAllMovies()
         //   getSlider()



        }
    }
}