package com.smcdeveloper.nobinoapp.navigation


import androidx.compose.runtime.Composable

import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.smcdeveloper.nobinoapp.ui.screens.bs.BoxScreen
import com.smcdeveloper.nobinoapp.ui.screens.categories.Categories
import com.smcdeveloper.nobinoapp.ui.screens.favorit.Favorit
import com.smcdeveloper.nobinoapp.ui.screens.home.Home
import com.smcdeveloper.nobinoapp.ui.screens.login.Login
import com.smcdeveloper.nobinoapp.ui.screens.profile.Profile
import com.smcdeveloper.nobinoapp.ui.screens.search.Search
import com.smcdeveloper.nobinoapp.ui.screens.signup.SignUp
import com.smcdeveloper.nobinoapp.ui.screens.splash.SplashScreen


@Composable
fun SetupNavGraph(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route


    )


    {

        composable(route = Screen.Splash.route) {
            SplashScreen(navController = navController)


        }
        composable(route = Screen.Login.route) {
            Login(navController = navController)


        }
        composable(route = Screen.SignUp.route) {

            SignUp(navController = navController)


        }
        composable(route = Screen.BoxScreen.route) {
            BoxScreen(navController = navController)


        }

        composable(route = Screen.Search.route) {
          Search(navController = navController)


        }
        composable(route = Screen.Profile.route) {
          Profile(navController = navController)


        }
          composable(route = Screen.Favorit.route) {
          Favorit(navController = navController)


        }

        composable(route = Screen.Categories.route) {
           Categories(navController = navController)


        }

        composable(route = Screen.Home.route) {
            Home(navController = navController)


        }







    }
}








