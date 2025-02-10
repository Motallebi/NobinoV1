package com.smcdeveloper.nobinoapp.navigation

import android.util.Log

sealed class Screen (val route:String) {

    data object Splash : Screen("splash_screen")
    data object Search : Screen("search_screen")
    data object Profile : Screen("profile_screen")
    data object Categories : Screen("categories_screen")
    data object Series : Screen("series_screen")
    data object Movies : Screen("movies_screen")

    data object Favorit : Screen("favorit_screen")
    data object Training : Screen("training_screen")


    data object Login : Screen("login_screen")
    data object SignUp : Screen("SignUp_screen")
    data object BoxScreen : Screen("Box_screen")
    data object Home : Screen("Home_Screen")
    data object Product : Screen("Product_Screen")
    data object ProductDetails : Screen("Product_Detail_Screen")
    data object OtpValidation : Screen("Otp_Validation_Screen")
    data object DemoScreen : Screen("Demo_Screen")
    data object VideoPlayerScreen : Screen("VideoPlayer_Screen")
    data object VideoDemoScreen : Screen("VideoDemo_Screen")
    data object SeriesDetailScreen : Screen("SeriesDetail_Screen")


    data object EditProfile : Screen("edit_profile_screen")
    data object BuySubscription : Screen("buy_subscription_screen")
    data object PaymentHistory : Screen("payment_history_screen")
    data object FAQ : Screen("faq_screen")
    data object TermsAndConditions : Screen("terms_conditions")
    data object ContactUs : Screen("contact_us")
    data object Logout : Screen("logout")













    fun withArgs(vararg args: Any): String {
        return buildString {
            append(route)
            args.forEach {
                append("/$it")
            }


        }




    }


    fun withArgs2(vararg args: Any): String {
        val result= buildString {
            append(route)
            args.forEach {
                append("/$it")
            }


        }

        Log.d("args","arg param is ${result}")
        return result




    }


















}