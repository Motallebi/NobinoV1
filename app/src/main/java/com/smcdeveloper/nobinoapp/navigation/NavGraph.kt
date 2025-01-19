package com.smcdeveloper.nobinoapp.navigation


import OtpValidationScreen
import ProductDetailPage
import RegisterScreen
import androidx.compose.runtime.Composable

import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

import com.smcdeveloper.nobinoapp.ui.screens.product.ProductScreen
import com.smcdeveloper.nobinoapp.ui.screens.bs.BoxScreen
import com.smcdeveloper.nobinoapp.ui.screens.categories.Categories
import com.smcdeveloper.nobinoapp.ui.screens.demo.DemoScreen
import com.smcdeveloper.nobinoapp.ui.screens.demo.VideoDemo
import com.smcdeveloper.nobinoapp.ui.screens.demo.VideoPlayScreen
import com.smcdeveloper.nobinoapp.ui.screens.demo.VideoScreen
import com.smcdeveloper.nobinoapp.ui.screens.favorit.Favorit
import com.smcdeveloper.nobinoapp.ui.screens.home.HomeScreen
import com.smcdeveloper.nobinoapp.ui.screens.login.LoginScreen
import com.smcdeveloper.nobinoapp.ui.screens.profile.ProfileScreen
import com.smcdeveloper.nobinoapp.ui.screens.search.Search
import com.smcdeveloper.nobinoapp.ui.screens.series.SeriesDetailPage
import com.smcdeveloper.nobinoapp.ui.screens.series.SeriesScreen

import com.smcdeveloper.nobinoapp.ui.screens.splash.SplashScreen
import java.net.URLDecoder
import java.nio.charset.StandardCharsets


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
            LoginScreen(navController = navController)


        }
        composable(route = Screen.SignUp.route) {

            RegisterScreen(navController = navController)


        }
        composable(route = Screen.BoxScreen.route) {
            BoxScreen(navController = navController)


        }

        composable(route = Screen.Search.route) {
            Search(navController = navController)


        }
        composable(route = Screen.Profile.route) {
            ProfileScreen(navController = navController)


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



        composable(
            route = Screen.OtpValidation.route + "/{refNumber}",
            arguments = listOf(navArgument("refNumber")
            { type = NavType.StringType })


        )


        {

                backStackEntry ->
            val refNumber = backStackEntry.arguments?.getString("refNumber") ?: ""
            OtpValidationScreen(navController = navController, refNumber = refNumber)


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


        composable(route = Screen.Product.route + "/{tags}/{category}",
            arguments = listOf(
                navArgument("tags") {
                    type = NavType.StringType


                },

                navArgument("category") {
                    type = NavType.StringType
                }


            )


        )


        {
            val tag = it.arguments?.getString("tags")
            val category = it.arguments?.getString("category")

            ProductScreen(
                navController = navController, tag = tag.toString(),
                categoryName = category.orEmpty()


            )


        }


        composable(route = Screen.ProductDetails.route + "/{productId}",
            arguments = listOf(
                navArgument("productId")
                {
                    type = NavType.IntType

                }

            )
        )


        {
            val productId = it.arguments?.getInt("productId")
            if (productId != null) {
                ProductDetailPage(
                    navController = navController,

                    productId = productId


                )
            }


        }


        composable(route = Screen.SeriesDetailScreen.route + "/{productId}",
            arguments = listOf(
                navArgument("productId")
                {
                    type = NavType.IntType

                }

            )
        )


        {
            val productId = it.arguments?.getInt("productId")
            if (productId != null) {
               SeriesDetailPage(
                    navController = navController,

                    productId = productId


                )
            }


        }






















        composable(route = Screen.DemoScreen.route) {
            DemoScreen(navController = navController)


        }




        composable(route = Screen.VideoPlayerScreen.route + "/{videoUrl}",
            arguments = listOf(
                navArgument("videoUrl")
                {
                    type = NavType.StringType

                }


            )


        )
        {

            val videoUrl = it.arguments?.getString("videoUrl").toString()
            val videoUrlDecode = URLDecoder.decode(videoUrl, StandardCharsets.UTF_8.toString())
            VideoPlayScreen(
                navController = navController,
                videourl = videoUrlDecode


            )


        }


        composable(route = Screen.VideoDemoScreen.route + "/{videoUrl}",


            arguments = listOf(
                navArgument("videoUrl")
                {
                    type = NavType.StringType

                }

            )
        )


        {
            val videoUrl = it.arguments?.getString("videoUrl")
            val videoUrlDecode = URLDecoder.decode(videoUrl, StandardCharsets.UTF_8.toString())
            if (videoUrl != null) {
                VideoDemo(
                    navController = navController,
                    videUrl = videoUrlDecode


                )


            }


        }
    }
}













