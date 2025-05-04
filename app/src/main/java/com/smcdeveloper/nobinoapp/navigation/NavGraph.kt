package com.smcdeveloper.nobinoapp.navigation


import OtpValidationScreen
import RegisterScreen
import SubscriptionSelectionPage
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.smcdeveloper.nobinoapp.ui.screens.Actors.ActorScreen
import com.smcdeveloper.nobinoapp.ui.screens.bs.BoxScreen
import com.smcdeveloper.nobinoapp.ui.screens.categories.Categories
import com.smcdeveloper.nobinoapp.ui.screens.search.BottomSheetSearch
import com.smcdeveloper.nobinoapp.ui.screens.demo.VideoPlay
import com.smcdeveloper.nobinoapp.ui.screens.favorit.FavoriteScreen
import com.smcdeveloper.nobinoapp.ui.screens.home.HomeScreen
import com.smcdeveloper.nobinoapp.ui.screens.home.KidsScreen
import com.smcdeveloper.nobinoapp.ui.screens.login.LoginScreen
import com.smcdeveloper.nobinoapp.ui.screens.movie.MovieScreen
import com.smcdeveloper.nobinoapp.ui.screens.product.ProductDetailPage
import com.smcdeveloper.nobinoapp.ui.screens.product.ProductScreen
import com.smcdeveloper.nobinoapp.ui.screens.profile.ContactUs
import com.smcdeveloper.nobinoapp.ui.screens.profile.EditUserInfoPage
import com.smcdeveloper.nobinoapp.ui.screens.profile.NewMemberPage
import com.smcdeveloper.nobinoapp.ui.screens.profile.PaymentHistoryPage
import com.smcdeveloper.nobinoapp.ui.screens.profile.PaymentResultScreen
import com.smcdeveloper.nobinoapp.ui.screens.profile.ProfilePictureSelectionPage
import com.smcdeveloper.nobinoapp.ui.screens.profile.ProfileScreen
import com.smcdeveloper.nobinoapp.ui.screens.profile.SubscriptionConfirmationPage
import com.smcdeveloper.nobinoapp.ui.screens.search.Search
import com.smcdeveloper.nobinoapp.ui.screens.series.SeriesDetailPage
import com.smcdeveloper.nobinoapp.ui.screens.series.SeriesScreen
import com.smcdeveloper.nobinoapp.ui.screens.splash.SplashScreen
import com.smcdeveloper.nobinoapp.ui.screens.training.TrainingScreen
import com.smcdeveloper.nobinoapp.viewmodel.LoginViewModel
import java.net.URLDecoder
import java.nio.charset.StandardCharsets


@Composable
fun SetupNavGraph(navController: NavHostController,loginViewModel: LoginViewModel) {

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route


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
            ProfileScreen(navController = navController, loginViewModel = loginViewModel)


        }
        composable(route = Screen.Favorite.route) {
            FavoriteScreen(navController = navController)



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


        composable(
            route = "${Screen.DemoScreen.route}/?tags={tags}?categoryName={categoryName}?categoryId={categoryId}",
            arguments = listOf(
                navArgument("tags")

            {
                type =NavType.StringType

            },
                navArgument("categoryName")

                {
                    type =NavType.StringType

                },
                navArgument("categoryId")

                {
                    type =NavType.IntType

                },







                )


            )


        { backStackEntry->

            val tags = backStackEntry.arguments?.getString("tags") ?: ""
            val categoryName = backStackEntry.arguments?.getString("categoryName") ?: ""
            val categoryId = backStackEntry.arguments?.getInt("categoryName") ?: 1


            BottomSheetSearch(navController = navController, tags = tags, categoryName = categoryName, categoryId = categoryId)

        }



        composable(
            route = Screen.DemoScreen.route)


        {



            BottomSheetSearch(navController = navController, tags = "")

        }









           // MovieScreen1()
           // DemoDialogSearch(navController = navController)
            //TestScreen(navController)
           // test1(navController,viewModel)
           // FavoriteScreen(navController)
           // Search(navController)











        composable(
          //  val otpRoute = "${Screen.OtpValidation.route}/{refNumber}?name={name}&username={username}&avatarId={avatarId}"
          //  route = Screen.OtpValidation.route +"/{refNumber}?name={name}?username={username}&avatarId={avatarId}",
            route = "${Screen.OtpValidation.route}/{refNumber}?name={name}&username={username}&avatarId={avatarId}",
            arguments = listOf(
                navArgument("refNumber")
            {
                type = NavType.StringType
            },

                navArgument("name")
                {
                    type = NavType.StringType
                    defaultValue=""
                    nullable=true
                },


                navArgument("username")
                {
                    type = NavType.StringType
                    defaultValue=""
                    nullable=true
                },
                navArgument("avatarId")
                {
                    type = NavType.IntType
                    defaultValue=1

                }





                )


        )


        {
            backStackEntry->

            val refNumber = backStackEntry.arguments?.getString("refNumber") ?: ""
            OtpValidationScreen(navController = navController,
                refNumber = refNumber,
                name= backStackEntry.arguments?.getString("name").toString(),
                username= backStackEntry.arguments?.getString("username").toString(),
                avatarId =  backStackEntry.arguments?.getInt("avatarId") ?: 1,
                loginViewModel = loginViewModel


            )




        }






        composable(
            route = Screen.PaymentResult.route + "/{planId}/{discountCode}/{id}",
            deepLinks = listOf(
                navDeepLink {
                    uriPattern = "nobinoapp://payment_result_screen/{planId}/{discountCode}/{id}"
                }
            ),
            arguments = listOf(
                navArgument("planId")
                {
                    type = NavType.IntType
                },

                navArgument("discountCode")
                {
                    type = NavType.StringType
                }

            )






        )


        {

                backStackEntry ->
            val planId = backStackEntry.arguments?.getInt("planId") ?: 1
            val discountCode = backStackEntry.arguments?.getString("discountCode") ?: ""
            val id = backStackEntry.arguments?.getString("id") ?: ""



            PaymentResultScreen(planId = planId, discountCode = discountCode,id=id)


        }






















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

                    productId = productId,
                    loginViewModel = loginViewModel


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



        composable(route=Screen.NewMember.route)
        {
            NewMemberPage(navController= navController)

        }


        composable(route=Screen.NewMemberSelection.route)
        {
            ProfilePictureSelectionPage(navController= navController)

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

            VideoPlay(videoUrl=videoUrlDecode)


        }







        /////
        composable(route = Screen.Movies.route) {
            MovieScreen(navController = navController)


        }


        composable(route = Screen.Actors.route+"/{id}",

            arguments = listOf(navArgument("id")
            {
                type=NavType.IntType


            }





        )
        )

            {

                val id = it.arguments?.getInt("id")





                if (id != null) {
                    ActorScreen(navController = navController, actorId = id)
                }


        }




        composable(route = Screen.PaymentHistory.route) {
            PaymentHistoryPage(navController = navController)


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




                SubscriptionConfirmationPage(navController=navController, planid = id.toString())















        }










    }
}













