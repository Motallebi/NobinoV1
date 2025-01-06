package com.smcdeveloper.nobinoapp.navigation


import androidx.compose.runtime.Composable

import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.smcdeveloper.nobinoapp.ui.screens.Product.Product
import com.smcdeveloper.nobinoapp.ui.screens.Product.ProductScreen
import com.smcdeveloper.nobinoapp.ui.screens.bs.BoxScreen
import com.smcdeveloper.nobinoapp.ui.screens.categories.Categories
import com.smcdeveloper.nobinoapp.ui.screens.favorit.Favorit
import com.smcdeveloper.nobinoapp.ui.screens.home.Home
import com.smcdeveloper.nobinoapp.ui.screens.home.HomeScreen
import com.smcdeveloper.nobinoapp.ui.screens.login.Login
import com.smcdeveloper.nobinoapp.ui.screens.profile.Profile
import com.smcdeveloper.nobinoapp.ui.screens.search.Search
import com.smcdeveloper.nobinoapp.ui.screens.series.SeriesScreen
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
            HomeScreen(navController = navController)


        }



        composable(route = Screen.Series.route) {
            SeriesScreen(navController = navController)


        }




      /*  composable(route = Screen.Product.route + "{/tags}",

            arguments = listOf(
                navArgument("tags") {
                    type = NavType.StringType
                    defaultValue = ""
                    nullable = true
                }
            )
        )

        {

            it.arguments!!.getString("tag")?.let { tag ->
                ProductScreen(
                    navController = navController,
                    tag = tag
                )
            }





        }*/


        composable(route = Screen.Product.route+"/{tags}/{category}",
            arguments = listOf(
                navArgument("tags"){
                type=NavType.StringType


            },

                navArgument("category") {
                    type = NavType.StringType
                }


                )


        )


        {
            val tag = it.arguments?.getString("tags")
            val category =it.arguments?.getString("category")

            ProductScreen(
                navController = navController, tag = tag.toString(),
                categoryName = category.orEmpty()


            )


        }





    }



}








