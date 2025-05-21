package com.smcdeveloper.nobinoapp

import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.ActivityInfo
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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.layout.BeyondBoundsLayout
import androidx.compose.ui.text.TextStyle
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.smcdeveloper.nobinoapp.navigation.Screen
import com.smcdeveloper.nobinoapp.ui.theme.nobinoLarge
import com.smcdeveloper.nobinoapp.util.AppConfig
import com.smcdeveloper.nobinoapp.util.ConnectivityObserver
import com.smcdeveloper.nobinoapp.util.Constants.USER_LOGIN_STATUS
import com.smcdeveloper.nobinoapp.util.DigitHelper
import com.smcdeveloper.nobinoapp.util.NetworkConnectivityObserver
import com.smcdeveloper.nobinoapp.viewmodel.LoginViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController
    private lateinit var connectivityObserver: ConnectivityObserver


    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        connectivityObserver = NetworkConnectivityObserver(applicationContext)






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

                val status by connectivityObserver.observe().collectAsState(
                    initial = ConnectivityObserver.Status.Available
                )
                val loginViewModel:LoginViewModel= hiltViewModel()

                var showInternetDialoge by remember { mutableStateOf(false) }
                AppConfig()




                // CompositionLocalProvider(LocalLayoutDirection provides direction)
                CompositionLocalProvider(LocalLayoutDirection provides androidx.compose.ui.unit.LayoutDirection.Rtl)

                {

                    Scaffold(


                       // contentColor = Color.Red,
                       // containerColor = Color.Green,


                        contentWindowInsets =  WindowInsets(0.dp,0.dp,0.dp,0.dp),
                        //modifier = Modifier.background(Color.Red),

                      //  Modifier.consumeWindowInsets(WindowInsets.statusBars),


                        bottomBar = {
                            BottomNavigationBar(navController, onItemClick = {

                                navController.navigate(it.route)


                                {
                                    launchSingleTop = true
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }

                                    restoreState = true



                                }









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
                                       || currentRoute.toString().startsWith("favorite")
                                       || currentRoute.toString().startsWith("VideoPlayer_Screen")
                                       || currentRoute.toString().startsWith("Demo_Screen")






                                       )




                            )

                               NobinoTop(navController)


                        },



                       content =  {it->


                           when(status)
                           {
                               ConnectivityObserver.Status.Available->{
                                   showInternetDialoge=false
                                   Log.d("connect","Connection Status: ${ConnectivityObserver.Status.Available}")


                               }
                               ConnectivityObserver.Status.Unavailable->{
                                   Log.d("connect","Connection Status: ${ConnectivityObserver.Status.Unavailable}")

                                   Box(modifier = Modifier.fillMaxSize()
                                       .background(Color.Red),
                                       contentAlignment = Alignment.Center
                                   )
                                   {
                                       Text("No Internet Connection.... ",
                                           style = MaterialTheme.typography.nobinoLarge


                                           )



                                   }




                               }






                               else -> {
                                   showInternetDialoge=true


                                       AlertDialog(
                                           containerColor =Color.White,
                                           onDismissRequest = { showInternetDialoge = false },
                                           title = { Text("No InterNet") },
                                           text = { Text("Are you sure you want to exit?") },
                                           confirmButton = {
                                               TextButton(
                                                   onClick = {
                                                       showInternetDialoge = false
                                                       // Exit the app by finishing the activity
                                                      this.finish()
                                                   }
                                               ) {
                                                   Text("Exit")
                                               }
                                           },
                                           dismissButton = {
                                               TextButton(onClick ={showInternetDialoge=false}) {

                                                   Text("Cancel")
                                                   Log.d("connect","dialog Status: $showInternetDialoge")

                                               }
                                           }
                                       )












                                   Box(modifier = Modifier.fillMaxSize()
                                       .background(Color.Red),
                                       contentAlignment = Alignment.Center
                                   )
                                   {
                                       Text("No Internet Connection.... ",
                                           style = MaterialTheme.typography.nobinoLarge


                                       )



                                   }



                               }
                           }






                           Box( modifier = Modifier
                               .fillMaxSize()

                               .padding(it)
                               //.windowInsetsPadding(WindowInsets.statusBars)
                              // .consumeWindowInsets(it)

                              // .background(Color.Green)

                           )



                               {
                                   SetupNavGraph(navController,loginViewModel)








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
      //  SetupNavGraph(navController = navController, loginViewModel = )

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
               // SetupNavGraph(navController = navController)



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