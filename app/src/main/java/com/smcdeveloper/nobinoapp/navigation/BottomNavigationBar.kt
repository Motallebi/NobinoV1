package com.smcdeveloper.nobinoapp.navigation

import android.graphics.drawable.Icon
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.get
import com.smcdeveloper.nobinoapp.R
import com.smcdeveloper.nobinoapp.ui.theme.nobinoMedium

@Composable
fun BottomNavigationBar(
    navController: NavController,
    onItemClick: (BottomNavItem) -> Unit,
) {
    val items = listOf(
        BottomNavItem(
            name = stringResource(R.string.home),
            route = Screen.Home.route,
            selectedIcon = painterResource(R.drawable.bottom_nav_home_selected),
            deSelectedIcon = painterResource(R.drawable.bottom_nav_home_not_selected),


            ),
        BottomNavItem(
            name = stringResource(R.string.user_profile),
            route = Screen.Profile.route,
            selectedIcon = painterResource(R.drawable.bottom_nav_user_selected),
            deSelectedIcon = painterResource(R.drawable.bottom_nav_user_not_selected),


            ),

        BottomNavItem(
            name = stringResource(R.string.categories),
            route = Screen.Categories.route,
            selectedIcon = painterResource(R.drawable.bottom_nav_category_selected),
            deSelectedIcon = painterResource(R.drawable.bottom_nav_category_not_selected),


            ),
        BottomNavItem(
            name = stringResource(R.string.serach),
            route = Screen.DemoScreen.route,
            selectedIcon = painterResource(R.drawable.bottom_nav_serach_selected),
            deSelectedIcon = painterResource(R.drawable.bottom_nav_serach_not_selected),


            ),
        BottomNavItem(
            name = "test",
            route = Screen.SignUp.route,
            selectedIcon = painterResource(R.drawable.bottom_nav_serach_selected),
            deSelectedIcon = painterResource(R.drawable.bottom_nav_serach_not_selected),


            ),
        BottomNavItem(
            name = "Demo",
            route = Screen.Favorite.route,
            selectedIcon = painterResource(R.drawable.bottom_nav_serach_selected),
            deSelectedIcon = painterResource(R.drawable.bottom_nav_serach_not_selected),


            ),


        )


    val backStackEntry = navController.currentBackStackEntryAsState()


    var showBottomBar = backStackEntry.value?.destination?.route in items.map { it.route }

    val videoScreen = "VideoPlayer_Screen/{videoUrl}"
    val bottomBarRoutes = setOf<String>(
        Screen.ContactUs.route,
        Screen.EditProfile.route,
        Screen.PaymentHistory.route,
        Screen.BuySubscription.route,
        Screen.Product.route,
        Screen.Movies.route,
        Screen.DemoScreen.route,

        //  videoScreen


    )


    val currentRoute = backStackEntry.value?.destination?.route

    Log.d("nav", "Show Bottom Bar: ${showBottomBar}")
    Log.d("nav", "Current Rout is: $currentRoute")


    val hiddenBottomNavRoutes = setOf(
        Screen.Login.route,
        Screen.Splash.route,
        Screen.SignUp.route,
        // Screen.DemoScreen.route

        Screen.VideoPlayerScreen.route.startsWith("VideoPlayer").toString(),
        videoScreen
    )


    val showBottomBar1 = currentRoute !in hiddenBottomNavRoutes





    if (currentRoute in bottomBarRoutes || currentRoute?.startsWith(Screen.Product.route) == true

        || currentRoute?.startsWith(Screen.Series.route) == true

    ) {
        showBottomBar = true

    }





    if (showBottomBar1) {
        NavigationBar(
            containerColor = Color.Black,
            tonalElevation = 4.dp
        )


        {

            items.forEachIndexed { index, item ->


                val selected = item.route == backStackEntry.value?.destination?.route
                val backgroundColor =
                    if (selected) Color.Black else Color.Transparent // ✅ Match container color





                NavigationBarItem(
                    selected = selected,
                    onClick = {

                        navController.navigate(item.route) {

                            if(item.route==Screen.ProductDetails.route)
                            {
                                launchSingleTop =true
                                popUpTo(navController.graph[Screen.Home.route])

                            }


                            launchSingleTop = true
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            restoreState = true
                        }








                        onItemClick(
                            item


                        )
                    },


                    icon = {
                        Column(horizontalAlignment = Alignment.CenterHorizontally)
                        {
                            if (selected) {
                                Icon(
                                    modifier = Modifier.height(24.dp),
                                    painter = item.selectedIcon,
                                    contentDescription = item.name


                                )


                            } else {
                                Icon(
                                    modifier = Modifier.height(24.dp),
                                    painter = item.deSelectedIcon,
                                    contentDescription = item.name
                                )


                            }

                            Text(
                                text = item.name,
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.nobinoMedium,
                                modifier = Modifier.padding(top = 5.dp)
                            )


                        }


                    },

                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = if (selected) Color.Black else Color.Transparent, // ✅ Match container color
                        selectedIconColor = Color.Red,
                        selectedTextColor = Color.Red


                    )
                )


            }

        }
    }

}













