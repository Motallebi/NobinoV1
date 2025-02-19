package com.smcdeveloper.nobinoapp.ui.screens.bs

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.smcdeveloper.nobinoapp.R

import com.smcdeveloper.nobinoapp.ui.theme.kidsPageColor
import com.smcdeveloper.nobinoapp.util.Constants.IMAGE_BASE_URL
import com.smcdeveloper.nobinoapp.viewmodel.HomeViewModel


@Composable
fun test1(navController: NavHostController,viewModel: HomeViewModel)
{


   // DetailsScreen(navController, viewModel )
    Show3()

   // val navController = rememberNavController()
   // ShowMainContent(navController = navController, backgroundImageUrl = "")












  // Show1()
  //  TestBackGround()
   // MyApp(modifier = Modifier)

  /* Text("DEMO SCREEN",

   style = MaterialTheme.typography.headlineLarge

   )*/


}







@Composable
fun Show3()
{

    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setStatusBarColor(Color.Transparent) // Make status bar transparent

        systemUiController.setSystemBarsColor(Color.Transparent) // Apply for both status and nav bars
        //  systemUiController.setStatusBarDarkContentEnabled(false) // false = White icons, true = Dark icons
    }

      Box(modifier = Modifier.fillMaxSize()
          //.background(Color.Red)
        //  .padding(top = 30.dp)

      )
      {
          Box(modifier = Modifier.background(Color.Green))
          {
              Image(painterResource(R.drawable.m1),"",
                  contentScale = ContentScale.Fit



              )

          }
















      }



    Text("test")



}






@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Show2()
{
    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setStatusBarColor(Color.Transparent) // Make status bar transparent

        systemUiController.setSystemBarsColor(Color.Transparent) // Apply for both status and nav bars
        //  systemUiController.setStatusBarDarkContentEnabled(false) // false = White icons, true = Dark icons
    }

    Scaffold(modifier = Modifier.fillMaxSize()
        .background(Color.Green)

    ) {

        Text("Demooo.....")








    }







}








@Composable
fun DetailsScreen(navController: NavController, backgroundViewModel: HomeViewModel) {



    val systemUiController = rememberSystemUiController()
    SideEffect {
       systemUiController.setStatusBarColor(Color.Transparent) // Make status bar transparent

        systemUiController.setSystemBarsColor(Color.Transparent) // Apply for both status and nav bars
        //  systemUiController.setStatusBarDarkContentEnabled(false) // false = White icons, true = Dark icons
    }




    LaunchedEffect(Unit) {
        val imagePAth="https://vod.nobino.ir/vod/IMAGES/2024-12/11897/11897_1735633207344_IMAGES_BANNER.jpg"
        backgroundViewModel.updateBackground(imagePAth)
    }


    Column(
        modifier = Modifier.fillMaxSize()
            .background(Color.Gray),

        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Details Screen", fontSize = 20.sp, color = Color.White)

        Button(onClick = { navController.navigate("home") }) {
            Text("Go to Home")
        }
    }
}














@Composable
fun TestBackGround()
{

    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setStatusBarColor(Color.Transparent) // Make status bar transparent
        systemUiController.setSystemBarsColor(Color.Transparent) // Apply for both status and nav bars
      //  systemUiController.setStatusBarDarkContentEnabled(false) // false = White icons, true = Dark icons
    }






    Box(
        modifier = Modifier
            .fillMaxSize()
             .background(MaterialTheme.colorScheme.kidsPageColor)
            //.windowInsetsPadding(WindowInsets.ime)
            //.graphicsLayer(alpha = 0.2f)
        , // Set the yellow background

        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.kids), // Replace with your PNG file
            contentDescription = "Transparent PNG",
            modifier = Modifier.fillMaxSize()
                .height(250.dp)
            ,
              //  .graphicsLayer(alpha = 0.8f),
                    // Adjust size as needed

            contentScale = ContentScale.Crop // Keep aspect ratio
        )

        Text("test ......")



    }






}



@Composable
fun MyApp(modifier: Modifier) {

    val systemUiController = rememberSystemUiController()

    SideEffect {
        systemUiController.setSystemBarsColor(
            color = Color.Transparent, // Transparent status bar
            darkIcons = false // false = White icons, true = Dark icons
        )
    }
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Background Image (Behind Status Bar)
        Image(
            painter = painterResource(id = R.drawable.m1),
            contentDescription = "Top Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .align(Alignment.TopCenter)
        )

        // Content below
       /* Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(WindowInsets.statusBars.asPaddingValues()) // ✅ Correct padding
            // Avoid status bar overlap
        ) {
            Text("Your App Content", color = Color.White, fontSize = 18.sp)
        }*/
    }
}










@Composable
fun test2()
{


val context = LocalContext.current
SideEffect {
    (context as? Activity)?.window?.let {
        WindowCompat.setDecorFitsSystemWindows(it, false) // Hide status bar padding
    }
}

Box(modifier = Modifier.fillMaxSize()) {
    // Background Image covering status bar
    Image(
        painter = painterResource(id = R.drawable.b1), // Replace with your image
        contentDescription = "Header Image",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp) // Adjust height to match design
    )

    // Overlay: App bar icons
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 40.dp, start = 16.dp, end = 16.dp), // Adjust for status bar
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(onClick = { /* Search action */ }) {
            Icon(
                painter = painterResource(id = R.drawable.mobile_icon
                ), // Replace with your icon
                contentDescription = "Search",
                tint = Color.White
            )
        }
        IconButton(onClick = { /* Profile action */ }) {
            Icon(
                painter = painterResource(id = R.drawable.mobile_icon), // Replace with your icon
                contentDescription = "Profile",
                tint = Color.White
            )
        }
    }

    // Scrolling Content (LazyColumn)
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 250.dp) // Push content below the image
            .background(Color.Black) // Match dark theme
    ) {
        item {
            SectionTitle("ویژه") // First section title
        }
        item {
            MovieRow(movies = listOf("Movie 1", "Movie 2", "Movie 3")) // First LazyRow
        }
        item {
            SectionTitle("تماشای رایگان") // Second section title
        }
        item {
            MovieRow(movies = listOf("Movie 4", "Movie 5", "Movie 6")) // Second LazyRow
        }
    }
}
}





@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        color = Color.White,
        style = MaterialTheme.typography.titleLarge,
        modifier = Modifier.padding(16.dp)
    )
}

@Composable
fun MovieRow(movies: List<String>) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(movies) { movie ->
            MovieItem(movie)
        }
    }
}

@Composable
fun MovieItem(movie: String) {
    Card(
        modifier = Modifier
            .size(width = 150.dp, height = 100.dp),
        colors = CardDefaults.cardColors(containerColor = Color.DarkGray)
    ) {
        Text(
            text = movie,
            color = Color.White,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        )
    }
}

@Composable
fun Show()
{


   /* val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.isStatusBarVisible = false
    }*/
    val context = LocalContext.current
    val window = (context as? Activity)?.window

    SideEffect {
        window?.statusBarColor = android.graphics.Color.TRANSPARENT
    }


    Scaffold() { innerPadding->

        Box(
            modifier = Modifier.padding(innerPadding)
                .fillMaxSize()
                //.background(Color.Green)

        )
        {

            Image(
                painter = painterResource(id = R.drawable.b1), // Replace with your image
                contentDescription = "Header Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp) // Adjust height to match design
            )




        }




    }






}


@Composable
fun Show1() {
    val context = LocalContext.current
    val window = (context as? Activity)?.window

    SideEffect {
        window?.statusBarColor = android.graphics.Color.TRANSPARENT // Make status bar transparent

        // Ensure content is drawn behind the status bar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window?.setDecorFitsSystemWindows(false)
        } else {
            window?.decorView?.systemUiVisibility = (
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                            View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    )
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.b1), // Your image
                contentDescription = "Header Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp) // Adjust height
            )

            // Apply padding to prevent content from being overlapped by status bar
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(WindowInsets.statusBars.asPaddingValues()) // Keeps content below status bar
            ) {
                Text(
                    text = "Transparent Status Bar Example",
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
    }
}