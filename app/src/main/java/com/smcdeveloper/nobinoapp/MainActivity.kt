package com.smcdeveloper.nobinoapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.smcdeveloper.nobinoapp.navigation.BottomNavigationBar
import com.smcdeveloper.nobinoapp.navigation.SetupNavGraph
import com.smcdeveloper.nobinoapp.ui.theme.NobinoAppTheme

class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController




    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            navController = rememberNavController()






            NobinoAppTheme {

                Scaffold(


                    bottomBar ={
                        BottomNavigationBar(
                            navController,
                            onItemClick = {
                                navController.navigate(it.route)

                            }

                        )








                    } ,


                )
                {

                    SetupNavGraph(navController = navController)







                }





            }
        }
    }
}

