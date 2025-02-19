package com.smcdeveloper.nobinoapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.LayoutDirection
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

import androidx.compose.ui.layout.BeyondBoundsLayout
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import coil3.compose.AsyncImage
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.smcdeveloper.nobinoapp.ui.screens.bs.MyApp
import com.smcdeveloper.nobinoapp.viewmodel.HomeViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    lateinit var navController: NavHostController


    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false) // Extend content into the status bar

        enableEdgeToEdge()
        setContent {

            val backgroundViewModel: HomeViewModel = hiltViewModel()
            val backgroundImageUrl by backgroundViewModel.backgroundImageUrl.collectAsState()
            navController = rememberNavController()


            AppTheme {


               // CompositionLocalProvider(LocalLayoutDirection provides direction)
                CompositionLocalProvider(LocalLayoutDirection provides androidx.compose.ui.unit.LayoutDirection.Rtl)
                {

                    Log.d("image",backgroundImageUrl.toString())




                  //  ShowMainContent(navController=navController,backgroundImageUrl=backgroundImageUrl,backgroundViewModel=backgroundViewModel)

                    showScreen(navController,backgroundViewModel)




                  //  MyApp(modifier = Modifier)




                }




            }
        }
    }






}




@Composable
fun ShowMainContent1(navController: NavHostController)
{

    Log.d("theme", "dark theme" +isSystemInDarkTheme())
    //navController = rememberNavController()

    LocalelUtils.setLocale(LocalContext.current, USER_LANGUAGE)

    val direction = if (USER_LANGUAGE == ENGLISH_LANG) {
        androidx.compose.ui.unit.LayoutDirection.Ltr
    } else {


        androidx.compose.ui.unit.LayoutDirection.Rtl
    }






    Scaffold(
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
                                        if(!currentRoute.toString().startsWith("Demo_Screen1"))

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
                    //SetupNavGraph(navController)
                    //  ShowContent1()
                    // ShowLAzy(navController)
                    //ShowItems(navController)


                }



            }














        )












    }










@Composable
fun ShowMainContent2(navController: NavHostController,modifier: Modifier) {

    Log.d("theme", "dark theme" + isSystemInDarkTheme())

    LocalelUtils.setLocale(LocalContext.current, USER_LANGUAGE)

    val direction = if (USER_LANGUAGE == ENGLISH_LANG) {
        androidx.compose.ui.unit.LayoutDirection.Ltr
    } else {
        androidx.compose.ui.unit.LayoutDirection.Rtl
    }

    // ✅ Allow full-screen layout in MainActivity (Make sure this is set)
    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setSystemBarsColor(
            color = Color.Transparent, // Transparent status bar
            darkIcons = false // false = White icons, true = Dark icons
        )
    }

    Scaffold(

        bottomBar = {
            BottomNavigationBar(navController) { item ->
                navController.navigate(item.route)
            }
        },
        topBar = {
            val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
            Log.d("topbar", currentRoute.toString())

            if (!currentRoute.toString().startsWith("Demo_Screen")) {
                NobinoTop(navController)
            }
        },
        content =
        { paddingValues -> // Pass Scaffold padding values

        MyApp(modifier.padding(paddingValues))

            Box(
                modifier
                    .fillMaxSize()
                    //.padding(paddingValues) // ✅ Applies padding only to content
            )

            {
                // ✅ Background image should NOT be inside Scaffold content padding
                //BackgroundImage()
                //MyApp(modifier = Modifier.padding(paddingValues))

               // ✅ Navigation content (SetupNavGraph)
              //  SetupNavGraph(navController)
            }
        }
    )
}
@Composable
fun BackgroundImage() {
    Box(
        Modifier.fillMaxWidth() // ✅ Make sure the Box is full width
    )
    {
        Box(

            modifier = Modifier
                .background(Color.Gray)
                .fillMaxWidth()
                .height(250.dp) // Adjust height as needed
                .align(Alignment.TopCenter) // ✅ Correctly place inside Box
        )
    }
}






@Composable
fun ShowMainContent3(navController: NavHostController) {

    Log.d("theme", "dark theme" + isSystemInDarkTheme())

    LocalelUtils.setLocale(LocalContext.current, USER_LANGUAGE)

    val direction = if (USER_LANGUAGE == ENGLISH_LANG) {
        androidx.compose.ui.unit.LayoutDirection.Ltr
    } else {
        androidx.compose.ui.unit.LayoutDirection.Rtl
    }

    // ✅ Allow full-screen layout in MainActivity (Make sure this is set)
    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setSystemBarsColor(
            color = Color.Transparent, // Transparent status bar
            darkIcons = false // false = White icons, true = Dark icons
        )
    }



    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController) { item ->
                navController.navigate(item.route)
            }
        },
        topBar = {
            val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
            if (!currentRoute.toString().startsWith("Demo_Screen")) {
                NobinoTop(navController)
            }
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier.fillMaxSize() // ✅ Full screen
            ) {
                // ✅ Background Box (extends behind status bar)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp) // Adjust height as needed
                        .background(Color.Red) // Change to your desired background color
                        .align(Alignment.TopCenter)
                )

                // ✅ Main Content (Respects Scaffold padding)
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues) // Apply padding only to content, not background
                ) {
                   // SetupNavGraph(navController)
                }
            }
        }
    )






}




@Composable
fun ShowMainContent4(navController: NavHostController, backgroundImageUrl: String?) {
    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController) { item ->
                navController.navigate(item.route)
            }
        },
        topBar = {
            val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
            if (!currentRoute.toString().startsWith("Demo_Screen")) {
                NobinoTop(navController)
            }
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                // ✅ Show background image if available, else use default color
                if (!backgroundImageUrl.isNullOrEmpty()) {
                    AsyncImage(
                        model = backgroundImageUrl,
                        contentDescription = "Background Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp)
                            .background(Color.Gray)
                            .align(Alignment.TopCenter)
                    )
                }

                // ✅ Main Content with padding applied
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues) // Apply padding only to content
                ) {
                   // SetupNavGraph(navController)
                }
            }
        }
    )
}







@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun showScreen(navController: NavHostController,
               backgroundViewModel: HomeViewModel

)
{
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    Scaffold(
        contentWindowInsets = WindowInsets(0.dp,0.dp,0.dp,0.dp),

        bottomBar = {
            BottomNavigationBar(navController) { item ->
                navController.navigate(item.route)
            }
        },
        topBar = {
            if (currentRoute != null && !currentRoute.startsWith("Product_Detail")) {
                NobinoTop(navController)
            }
        },








    ) {

        SetupNavGraph(navController, backgroundViewModel)
    }




    }









@Composable
fun ShowMainContent(
    navController: NavHostController,
    backgroundImageUrl: String?,
    backgroundViewModel: HomeViewModel
) {

    LaunchedEffect(Unit) {
       // backgroundViewModel.resetBackground()


    }



    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route



    // ✅ Keep home screen background static
    val finalBackgroundImage:Any?  = if (currentRoute == "home") {
        R.drawable.kids // Replace with your local drawable
    } else {
        backgroundImageUrl // Use dynamic background for other pages
    }

    Scaffold(
        contentWindowInsets = WindowInsets(0.dp,0.dp,0.dp,0.dp),

        bottomBar = {
            BottomNavigationBar(navController) { item ->
                navController.navigate(item.route)
            }
        },
        topBar = {
            if (currentRoute != null && !currentRoute.startsWith("Demo_Screen")) {
                NobinoTop(navController)
            }
        },
        content = { paddingValues ->
            Box(modifier = Modifier.fillMaxSize()
                //.background(Color.)

            ) {
                // ✅ Show background image (static for home, dynamic for others)

                when (finalBackgroundImage) {
                    is Int -> { // If it's a local drawable
                        Image(
                            painter = painterResource(id = finalBackgroundImage),
                            contentDescription = "Home Background Image",
                            contentScale = ContentScale.Inside

                            ,
                           // modifier = Modifier.fillMaxSize()
                        )
                    }
                    is String -> { // If it's a remote image URL
                        AsyncImage(
                            model = finalBackgroundImage,
                            contentDescription = "Background Image",
                            contentScale = ContentScale.Inside,
                            modifier = Modifier.height(700.dp)

                           // modifier = Modifier.fillMaxWidth()
                        )
                    }
                    else -> { // Default background if no image is available
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(250.dp)
                                .background(Color.Gray)
                                .align(Alignment.TopCenter)
                        )
                    }
                }


















                // ✅ Main Content
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    SetupNavGraph(navController, backgroundViewModel)
                }
            }
        }
    )
}


























@Composable
fun ShowItems(navController:NavHostController)
{
    Column(

       /* modifier = Modifier.verticalScroll(rememberScrollState())
            .fillMaxSize()*/


    ) {
       // SetupNavGraph(navController = navController)

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