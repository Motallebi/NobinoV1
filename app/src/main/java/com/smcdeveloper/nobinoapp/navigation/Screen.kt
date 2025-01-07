package com.smcdeveloper.nobinoapp.navigation

sealed class Screen (val route:String) {

    data object Splash : Screen("splash_screen")
    data object Search : Screen("search_screen")
    data object Profile : Screen("profile_screen")
    data object Categories : Screen("categories_screen")
    data object Series : Screen("series_screen")

    data object Favorit : Screen("favorit_screen")


    data object Login : Screen("login_screen")
    data object SignUp : Screen("SignUp_screen")
    data object BoxScreen : Screen("Box_screen")
    data object Home : Screen("Home_Screen")
    data object Product : Screen("Product_Screen")
    data object ProductDetails : Screen("Product_Detail_Screen")



    fun withArgs(vararg args: Any): String {
        return buildString {
            append(route)
            args.forEach {
                append("/$it")
            }


        }


    }
}