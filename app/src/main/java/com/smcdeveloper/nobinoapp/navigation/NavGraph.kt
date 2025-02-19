package com.smcdeveloper.nobinoapp.navigation


import OtpValidationScreen
import ProductDetailPage
import RegisterScreen
import SubscriptionSelectionPage
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.smcdeveloper.nobinoapp.R

import com.smcdeveloper.nobinoapp.ui.screens.product.ProductScreen
import com.smcdeveloper.nobinoapp.ui.screens.bs.BoxScreen
import com.smcdeveloper.nobinoapp.ui.screens.bs.TestScreen
import com.smcdeveloper.nobinoapp.ui.screens.bs.test1
import com.smcdeveloper.nobinoapp.ui.screens.categories.Categories
import com.smcdeveloper.nobinoapp.ui.screens.demo.BasicSearchScreen
import com.smcdeveloper.nobinoapp.ui.screens.demo.DemoBottomSheetSearch
import com.smcdeveloper.nobinoapp.ui.screens.demo.DemoDialogSearch

import com.smcdeveloper.nobinoapp.ui.screens.demo.DemoModalSearch
import com.smcdeveloper.nobinoapp.ui.screens.demo.DemoScreen
import com.smcdeveloper.nobinoapp.ui.screens.demo.DemoSearch
import com.smcdeveloper.nobinoapp.ui.screens.demo.DemoSearchWithModal
import com.smcdeveloper.nobinoapp.ui.screens.demo.MovieScreen1
import com.smcdeveloper.nobinoapp.ui.screens.demo.VideoDemo
import com.smcdeveloper.nobinoapp.ui.screens.demo.VideoPlayScreen
import com.smcdeveloper.nobinoapp.ui.screens.demo.VideoScreen
import com.smcdeveloper.nobinoapp.ui.screens.favorit.Favorit
import com.smcdeveloper.nobinoapp.ui.screens.home.HomeScreen
import com.smcdeveloper.nobinoapp.ui.screens.home.Kids
import com.smcdeveloper.nobinoapp.ui.screens.home.KidsScreen
import com.smcdeveloper.nobinoapp.ui.screens.login.LoginScreen
import com.smcdeveloper.nobinoapp.ui.screens.movie.MovieScreen
import com.smcdeveloper.nobinoapp.ui.screens.profile.ContactUs
import com.smcdeveloper.nobinoapp.ui.screens.profile.EditUserInfoPage
import com.smcdeveloper.nobinoapp.ui.screens.profile.ProfileScreen
import com.smcdeveloper.nobinoapp.ui.screens.profile.SubscriptionConfirmationPage
import com.smcdeveloper.nobinoapp.ui.screens.search.Search
import com.smcdeveloper.nobinoapp.ui.screens.series.SeriesDetailPage
import com.smcdeveloper.nobinoapp.ui.screens.series.SeriesScreen

import com.smcdeveloper.nobinoapp.ui.screens.splash.SplashScreen
import com.smcdeveloper.nobinoapp.ui.screens.training.TrainingScreen
import com.smcdeveloper.nobinoapp.viewmodel.HomeViewModel
import java.net.URLDecoder
import java.nio.charset.StandardCharsets


@Composable
fun SetupNavGraph(navController: NavHostController,viewModel: HomeViewModel) {

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
            Log.d("navhost","Profile Screen")
            ProfileScreen(navController = navController)


        }
        composable(route = Screen.Favorit.route) {
            Favorit(navController = navController)


        }

        composable(route = Screen.Categories.route) {
            Categories(navController = navController)


        }

        composable(route = Screen.Home.route) {
            Log.d("navhost","Home Screen")
            HomeScreen(navController = navController)


        }










        composable(route = Screen.EditProfile.route) {
            EditUserInfoPage(navController = navController)


        }


        composable(route = Screen.ContactUs.route) {

            Log.d("navhost","Contact Us Scrren")
            ContactUs(navController)


        }





        composable(route = Screen.BuySubscription.route) {
            SubscriptionSelectionPage(navController = navController)


        }

        composable(route = Screen.FAQ.route) {
            HomeScreen(navController = navController)


        }

        composable(route = Screen.TermsAndConditions.route) {
            HomeScreen(navController = navController)


        }

        composable(route = Screen.Logout.route) {
            HomeScreen(navController = navController)


        }



























        composable(route = Screen.Series.route) {
            SeriesScreen(navController = navController)


        }


        composable(route = Screen.DemoScreen.route) {
            //DemoBottomSheetSearch(navController = navController)
           // MovieScreen1()
           // DemoDialogSearch(navController = navController)
            //TestScreen(navController)
            test1(navController,viewModel)



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


        composable(route = Screen.Product.route + "/{tags}/{category}/{categoryName}",




            arguments = listOf(
                navArgument("tags") {
                    type = NavType.StringType


                },

                navArgument("category") {
                    type = NavType.StringType
                },
                navArgument("categoryName")
                {
                    type=NavType.StringType

                }


            )


        )


        {


            val tagsJson = it.arguments?.getString("tags")
          //  val tagsList: List<String> = Gson().fromJson(tagsJson, object : TypeToken<List<String>>() {}.type)



            val tag = it.arguments?.getString("tags")
            val category = it.arguments?.getString("category")
            val categoryTitle =it.arguments?.getString("categoryName")
            Log.d("navg","tags is" +tag.toString())
            Log.d("navg","category is" +category.toString())
            //Log.d("navg","category is" +tagsList.toString())

            val tag2 = listOf(tag).filterNotNull()

            if (tag != null) {
                ProductScreen(
                    navController = navController,
                    tag = tag,
                    categoryName = category.orEmpty(),
                    categoryTitle = categoryTitle.orEmpty()



                )
            }


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

        composable(route=Screen.Kids.route)
        {
            KidsScreen(navController=navController)

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




        /////
        composable(route = Screen.Movies.route) {
            MovieScreen(navController = navController)


        }

        composable(route = Screen.Training.route) {
            TrainingScreen(navController = navController)


        }

        composable(route = Screen.SubscriptionConfirmation.route+"/{id}",

            arguments = listOf(navArgument("id")
            {
                type=NavType.StringType


            }

            )

        )


            {

            val id = it.arguments?.getString("id")


                Log.d("plan","plan id: $id")

                SubscriptionConfirmationPage(navController=navController, planid = id.toString())















        }










    }
}













