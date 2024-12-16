package com.smcdeveloper.nobinoapp.ui.screens.categories

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.smcdeveloper.nobinoapp.ui.theme.extraBoldNumber
import com.smcdeveloper.nobinoapp.util.Constants.LOG_TAG

@Composable
fun Categories(navController: NavHostController) {

    Log.d(LOG_TAG,"we arte in Categories Page")

    Box(
        contentAlignment = Alignment.TopCenter


    )
    {

        Text(
            "CATEGORIES",
           // style = MaterialTheme.typography.extraBoldNumber,



        )


    }
}