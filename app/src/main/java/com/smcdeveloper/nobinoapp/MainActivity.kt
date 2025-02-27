package com.smcdeveloper.nobinoapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.LayoutDirection
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection

import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.smcdeveloper.nobinoapp.navigation.BottomNavigationBar
import com.smcdeveloper.nobinoapp.navigation.SetupNavGraph
import com.smcdeveloper.nobinoapp.ui.screens.home.NobinoTop
import com.smcdeveloper.nobinoapp.ui.screens.home.NobinoTopBar
import com.smcdeveloper.nobinoapp.ui.theme.AppTheme
import com.smcdeveloper.nobinoapp.util.Constants.ENGLISH_LANG
import com.smcdeveloper.nobinoapp.util.Constants.USER_LANGUAGE
import com.smcdeveloper.nobinoapp.util.LocalelUtils
import dagger.hilt.android.AndroidEntryPoint
import androidx.compose.foundation.layout.*
import androidx.compose.ui.layout.BeyondBoundsLayout
import androidx.core.view.WindowCompat
import androidx.navigation.compose.currentBackStackEntryAsState
import com.smcdeveloper.nobinoapp.util.AppConfig

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController


    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false) // Extend content into the status bar

        enableEdgeToEdge()
        setContent {
            Log.d("theme", "dark theme" +isSystemInDarkTheme())
            navController = rememberNavController()

            LocalelUtils.setLocale(LocalContext.current, USER_LANGUAGE)

            val direction = if (USER_LANGUAGE == ENGLISH_LANG) {
                androidx.compose.ui.unit.LayoutDirection.Ltr
            } else {


                androidx.compose.ui.unit.LayoutDirection.Rtl
            }










            AppTheme {

                AppConfig()


               // CompositionLocalProvider(LocalLayoutDirection provides direction)
                CompositionLocalProvider(LocalLayoutDirection provides androidx.compose.ui.unit.LayoutDirection.Rtl)

                {

                    Scaffold(

                        contentWindowInsets =  WindowInsets(0.dp,0.dp,0.dp,0.dp),
                        //modifier = Modifier.background(Color.Red),

                      //  Modifier.consumeWindowInsets(WindowInsets.statusBars),


                        bottomBar = {
                            BottomNavigationBar(navController, onItemClick = {
                                navController.navigate(it.route)

                            }

                            )


                        },


                        topBar = {
                            val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
                            Log.d("topbar",currentRoute.toString())
                            if(

                               ! ( currentRoute.toString().startsWith("Product_Detail_Screen") ||
                                currentRoute.toString().startsWith("SeriesDetail_Screen") ||
                                       currentRoute.toString().startsWith("SignUp_screen")
                                       || currentRoute.toString().startsWith("VideoPlayer_Screen")





                                       )




                            )

                               NobinoTop(navController)


                        },



                       content =  {it->

                           Box( modifier = Modifier
                               .fillMaxSize()

                               .padding(it)
                               //.windowInsetsPadding(WindowInsets.statusBars)
                              // .consumeWindowInsets(it)

                              // .background(Color.Green)

                           )



                               {
                                   SetupNavGraph(navController)
                                 //  ShowContent1()
                                    // ShowLAzy(navController)
                               //ShowItems(navController)


                               }



                           }














                    )












                }


            }
        }
    }






}


@Composable
fun ShowItems(navController:NavHostController)
{
    Column(

       /* modifier = Modifier.verticalScroll(rememberScrollState())
            .fillMaxSize()*/


    ) {
        SetupNavGraph(navController = navController)

    }

}






@Composable
fun ShowLAzy(navController:NavHostController)
{




        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Gray)
            // .padding(bottom = 60.dp)
        )

        {






















            item {
                SetupNavGraph(navController = navController)



            }






        }















    }







@Composable
fun ShowContent(paddingValues: PaddingValues)

{
    Text("this test function!!",
        modifier = Modifier
            .background(Color.Red)
            .padding(paddingValues)





        )


}



@Composable
fun ShowContent1()

{
    Text("this test function!!",
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Red)
           // .padding(15.dp)





    )


}